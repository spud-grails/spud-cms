package spud

import spud.core.*
import spud.cms.*
import spud.permalinks.*

class PageController {
	def grailsApplication
	def spudTemplateService
	def spudPermalinkService

	static layout = null

	def show = {
		def urlName = params.id
		def siteId  = params.int('siteId') ?: 0

		if(!urlName) {
			urlName = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		}
		println("Finding Page ${urlName}")
		def page = SpudPage.findBySiteIdAndUrlName(siteId,urlName)
		if(!page) {
			println "Page Not Found"
			def permalink = spudPermalinkService.permalinkForUrl(params.id, siteId)
			if(permalink) {
				println "Rediecting to ${permalink.destinationUrl}"
				redirect(url: "/" + permalink.destinationUrl, permanent: true)
			} else {
				render status: 404
			}
			return
		}
		println "Rendering Page ${urlName}"
		def templateService = spudTemplateService.activeTemplateService(page.templateEngine)
		render templateService.render('/spud/page/show', [model: [page:page], layout: page.layout])

	}
}
