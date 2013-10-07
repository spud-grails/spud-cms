package spud.core

class DefaultSpudTemplateService {
	def grailsApplication

	def layoutsForSite(siteId=0) {
		def layouts = grailsApplication.config.spud.core.layouts
		def layoutArray = []
		layouts.each {
			layoutArray << [name: it.key, partials: it.value.collect { partial -> [name: partial.key, format: partial.value] }]
		}
		return layoutArray
	}

	def renderContent(page) {
		render view: '/page/show', model: [page: page]
	}

}
