package spud.core

class DefaultSpudTemplateService {
	def grailsApplication

	def layoutsForSite(siteId=0) {
		def layouts = grailsApplication.config.spud.core.layouts?.findAll{ !it.containsKey('siteId') || it.siteId == siteId}
		return layouts
	}

	def render(defaultView, options) {
		//Options available, view: 'file ref', content: 'content', model, objects to pass through
		return [view: defaultView] + options
	}

}
