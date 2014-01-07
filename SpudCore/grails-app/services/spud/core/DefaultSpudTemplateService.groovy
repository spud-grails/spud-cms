package spud.core

class DefaultSpudTemplateService {
	def grailsApplication

	def currentSiteId() {
		return 0
	}

	def layoutsForSite(siteId=0) {
		def layouts = grailsApplication.config.spud.core.layouts?.findAll{ !it.containsKey('siteId') || it.siteId == siteId}
		return layouts
	}

	def layoutForName(name, siteId=0) {
		def layouts = layoutsForSite(siteId)
		return layouts.find {it.name == name}
	}

	def render(defaultView, options) {
		//Options available, view: 'file ref', content: 'content', model, objects to pass through
		return [view: defaultView] + options
	}

}
