package spud.cms

import org.grails.web.servlet.mvc.GrailsWebRequest
import org.hibernate.FetchMode
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class UrlMappings {

	static mappings = { ctx ->
		Logger log = LoggerFactory.getLogger(UrlMappings.class)
		def grailsApplication = grails.util.Holders.grailsApplication
		def defaultSpudPage = grailsApplication?.config?.spud?.cms?.defaultPage ?: 'home'
		if(log.isTraceEnabled()) log.trace "defaultSpudPage: ${defaultSpudPage}"
		def FORBIDDEN = [
			'plugins',
			'WEB-INF',
			'assets',
			'console',
			'is-tomcat-running',
			'spud/admin'
		]

		"/spud/admin/pages"(resources: "pages", namespace: "spud_admin")
		"/spud/admin/pages/clear"(controller: "pages", namespace: "spud_admin", action: 'clear')
		"/spud/admin/pages/page_parts"(controller: "pages", namespace: "spud_admin", action: 'pageParts')
		"/spud/admin/pages/page_parts/$id"(controller: "pages", namespace: "spud_admin", action: 'pageParts')
		"/spud/admin/snippets"(resources: "snippets", namespace: "spud_admin")
		"/spud/admin/menus"(resources: "menus", namespace: "spud_admin")
		"/spud/admin/menus/$menusId/menu_items/sort"(controller: 'menuItems', namespace: "spud_admin", action: 'sort', method:"PUT")
		"/spud/admin/menus"(resources: "menus", namespace: "spud_admin") {
			"/menu_items"(resources: "menuItems", namespace: "spud_admin")
		}

		"/$id**?" {
			controller = 'spudPage'
			action = 'show'
			cacheEnabled = grailsApplication?.config?.spud?.cms?.cacheEnabled && grailsApplication?.config?.spud?.cms?.cacheMode != 'partial'
			constraints {
				id(validator: { id ->
					if(FORBIDDEN.find{ forbidden -> id?.startsWith(forbidden)}) {
						if(log.isDebugEnabled()) log.debug "starts with forbidden returning false"
						return false
					}
					if(log.isDebugEnabled()) log.debug "id: ${id}"
					def siteId = GrailsWebRequest.lookup().getAttribute('spudSiteId',0)
					if(log.isDebugEnabled()) log.debug "siteId: ${siteId}"
					def urlName = id ?: defaultSpudPage
					if(log.isDebugEnabled()) log.debug "urlName: ${urlName}"
					def page = SpudPage.withCriteria(readOnly:true, uniqueResult:true, cache:true) {
						eq('siteId',siteId)
						eq('published',true)
						eq('urlName', urlName)
						eq('published',true)
						fetchMode 'partials', FetchMode.JOIN
					}
					if(log.isTraceEnabled()) log.trace "page: ${page}"
					if(!page) {
						if(log.isDebugEnabled()) log.debug "page was null"
						return false
					} else {
						if(page.visibility == 1) {
							if(log.isInfoEnabled()) log.info "cacheEnabled set to false"
							cacheEnabled = false
						}
						if(log.isDebugEnabled()) log.debug "returning true"
						return true
					}

				})
			}
		}

	}
}
