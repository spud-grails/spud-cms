package spud

import spud.core.*
import spud.cms.*
import spud.permalinks.*
import org.hibernate.FetchMode
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable

class PageController {
	def grailsApplication
	def spudLayoutService
	def spudPermalinkService

	static layout = null
	// static namespace = 'spud'

	@Cacheable('spud.cms.page')
	def show() {
		def urlName = params.id
		def siteId = params.int('siteId') ?: 0
		if(!urlName) {
			urlName = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		}
		def page = request.getAttribute('spudPage')
		if(!page) {
			page = SpudPage.withCriteria(readOnly:true, uniqueResult:true, cache:true) {
				eq('siteId',siteId)
				eq('urlName', urlName)
				fetchMode 'partials', FetchMode.JOIN
			}
		}

		if(!page) {
			println "Page Not Found"
			render status: 404
			return
		}

		render template: '/spud/page/show', model: [page:page], layout: null

	}
}
