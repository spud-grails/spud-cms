package spud.core

import grails.transaction.Transactional

@Transactional
class DefaultSpudRendererService {
	def defaultSpudTemplateService
	def render(options) {
		// Grab this pages template Service and pass it on
		def spudTemplateService = options.templateService ?: defaultSpudTemplateService
		spudTemplateService.renderContent(options.page)
	}
}
