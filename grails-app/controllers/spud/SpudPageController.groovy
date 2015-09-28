package spud

import spud.core.*
import spud.cms.*
import spud.permalinks.*
import org.hibernate.FetchMode
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SpudPageController {
	def grailsApplication
	def spudLayoutService
	def spudPermalinkService


	static layout = null
	// static namespace = 'spud'

	@Cacheable(value='spud.cms.page', condition="#cacheEnabled == true")
	def show(Boolean cacheEnabled) {
		def urlName = params.id
		def siteId = request.getAttribute('spudSiteId')
		if(!urlName) {
			urlName = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		}
		def page = request.getAttribute('spudPage')
		if(!page) {
			page = SpudPage.withCriteria(readOnly:true, uniqueResult:true, cache:true) {
				eq('siteId',siteId)
				eq('published',true)
				eq('urlName', urlName)
				fetchMode 'partials', FetchMode.JOIN
			}
		}

		if(!page) {
			log.debug "Page Not Found"
			render status: 404
			return
		}

		render template: '/spud/page/show', model: [page:page], layout: null

	}
}
