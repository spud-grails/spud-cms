package spud

import spud.core.*
import spud.cms.*

class PageController {
	def grailsApplication
	def spudTemplateService

	def show = {
		def urlName = params.id

		if(!urlName) {
			urlName = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		}
		def page = SpudPage.findByUrlName(urlName)
		if(!page) {
			// TODO : CHECK THE PERMALINKS
			render status: 404
			return
		}

		spudTemplateService.render(page)
	}
}
