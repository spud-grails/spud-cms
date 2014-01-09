package spud.core

import org.apache.tools.ant.DirectoryScanner
import org.codehaus.groovy.grails.plugins.GrailsPluginUtils

class DefaultSpudLayoutService {
	static transactional = false
	def grailsApplication
	def dynamicOnWarDeploy = false

	def currentSiteId() {
		return 0
	}

	def layoutsForSite(siteId=0) {
		def layouts = []
		if(grailsApplication.warDeployed) {
			layouts = grailsApplication.config.spud.core.layouts
		} else {
			layouts = scanForLayouts(layoutPaths())
		}

		return layouts
	}

	// Fetches The Actual Layout File Contents for parsing
	// **NOTE** Should not be used in War mode for system layout
	def layoutContents(name) {
		if(grailsApplication.warDeployed) {
			log.error = "Layout Contents Are being fetched in war mode. This is NOT supported."
			return null
		}

		def file = fileForLayout(name)
		if(file) {
			return file.text
		} else {
			return null
		}
	}

	def layoutForName(name, siteId=0) {
		def layouts = layoutsForSite(siteId)
		return layouts.find {it.name == name}
	}

	def render(defaultView, options) {
		//Options available, view: 'file ref', content: 'content', model, objects to pass through
		return [view: defaultView] + options
	}

	private layoutPaths() {
		def paths = []
		paths << new File("grails-app/views/layouts").getAbsolutePath()

		for(plugin in GrailsPluginUtils.pluginInfos) {
			paths << [plugin.pluginDir.getPath(), "grails-app", "views/layouts"].join(File.separator)
		}
		return paths
	}

	private fileForLayout(name) {
		def paths = layoutPaths()
		def layoutFile = null
		paths.find { path ->
			def file = new File(path, name + ".gsp")
			if(file.exists()) {
				layoutFile = file
				return true
			} else {
				return false
			}
		}
		return layoutFile
	}

	private scanForLayouts(paths) {
		DirectoryScanner scanner = new DirectoryScanner()
		def filesToProcess       = []

		paths.each { path ->
			if(new File(path).exists()) {
				scanner.setIncludes(["**/*.gsp"] as String[])
				scanner.setBasedir(path)
				scanner.setCaseSensitive(false)
				scanner.scan()
				filesToProcess += scanner.getIncludedFiles().flatten()
			}
		}

		filesToProcess.unique()
		filesToProcess = filesToProcess.collect { fileName ->
			def extensionIndex = fileName.lastIndexOf('.gsp')
			return extensionIndex != -1 ? fileName.substring(0,extensionIndex) : fileName
		}

		return filesToProcess
	}

}
