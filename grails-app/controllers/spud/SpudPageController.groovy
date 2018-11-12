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

	def spudLayoutService
	def spudPermalinkService
	def sharedSecurityService

	static layout = null
	// static namespace = 'spud'

//	@Cacheable(value='spud.cms.page')	CURSE THIS PLUGIN USAGE!!!!! not compatible with spud atm. Probably need to provide spud page unique id in order to differentiate
	def show(Boolean cacheEnabled) {
		log.debug "show Boolean cacheEnabled params: ${params}"
		def urlName = params.id
		log.debug "show urlName: ${urlName}"
		def siteId = request.getAttribute('spudSiteId')
		log.debug "show siteId: ${siteId}"
		if(!urlName) {
			urlName = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		}
		def page = request.getAttribute('spudPage')
		log.debug "show page: ${page}"
		if(!page) {
			page = SpudPage.withCriteria(readOnly:true, uniqueResult:true, cache:true) {
				eq('siteId',siteId)
				eq('published',true)
				eq('urlName', urlName)
				eq('published',true)
				fetchMode 'partials', FetchMode.JOIN
			}
		}

		if(!page) {
			log.debug "Page Not Found"
			render status: 404
			return
		} else if(page.visibility == 1 && !sharedSecurityService.currentUser) {
			log.debug "Page Restricted"
			sharedSecurityService.storeLocation(request)
			redirect sharedSecurityService.createLink('login')
			return
		}
		log.debug "show page: ${page}"

//		render view: '/spud/page/show', model: [page:page], layout: null
//		render template: '/show', model: [page:page], layout: null
		render template: '/spud/page/show', model: [page:page], layout: null
	}
}
