package spud

import spud.core.*
import spud.cms.*
import spud.permalinks.*

class PageController {
	def grailsApplication
	def spudLayoutService
	def spudPermalinkService

	static layout = null

	def show = {
		def urlName = params.id
		def siteId  = params.int('siteId') ?: 0

		if(!urlName) {
			urlName = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		}
		def page = request.getAttribute('spudPage') ?: SpudPage.findBySiteIdAndUrlName(siteId,urlName)

		if(!page) {
			println "Page Not Found"
			render status: 404
			return
		}
		println "Rendering Page ${urlName}"

		// def layoutService = spudLayoutService.layoutServiceForSite(siteId)
		// println "Layouts: ${layoutService.layoutsForSite()}"
		render view: '/spud/page/show', model: [page:page], layout: page.layout

	}
}
