import org.codehaus.groovy.grails.plugins.GrailsPluginUtils
import org.apache.tools.ant.DirectoryScanner
import groovy.util.ConfigSlurper

eventCreateWarStart = { name, stagingDir ->
	event("StatusUpdate",["Compiling Spud Layout Cache"])

	DirectoryScanner scanner = new DirectoryScanner()
	def layouts              = []
	def paths                = []

	paths << new File('grails-app/views/layouts').getAbsolutePath()

	for(plugin in GrailsPluginUtils.pluginInfos) {
		paths << [plugin.pluginDir.getPath(), 'grails-app/views/layouts'].join(File.separator)
	}


	paths.each { path ->
		if(new File(path).exists()) {
			scanner.setIncludes(["**/*.gsp"] as String[])
			scanner.setBasedir(path)
			scanner.setCaseSensitive(false)
			scanner.scan()
			layouts += scanner.getIncludedFiles().flatten()
		}
	}
	layouts.unique()
	layouts = layouts.collect { fileName ->
		def extensionIndex = fileName.lastIndexOf('.gsp')
		return extensionIndex != -1 ? fileName.substring(0,extensionIndex) : fileName
	}
	layoutList = new File(stagingDir,'WEB-INF/spudLayouts.txt')
	layoutList.createNewFile()
	layoutList.text = layouts.join("\n")


}
