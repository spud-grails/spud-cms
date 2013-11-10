package spud

import spud.core.*
import spud.cms.*

class PageController {
	def grailsApplication
	def spudTemplateService

	static layout = null

	def show = {
		println "Showing the Page!"
		def urlName = params.id

		if(!urlName) {
			urlName = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		}
		def page = SpudPage.findByUrlName(urlName)
		println "Finding Page ${urlName}"
		if(!page) {
			// TODO : CHECK THE PERMALINKS
			render status: 404
			return
		}
		def templateService = spudTemplateService.activeTemplateService(page.templateEngine)
		render templateService.render('/spud/page/show', [model: [page:page], layout: page.layout])

	}
}
