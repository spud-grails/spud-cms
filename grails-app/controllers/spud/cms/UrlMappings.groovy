package spud.cms

import groovy.util.logging.Slf4j
import org.grails.web.servlet.mvc.GrailsWebRequest
import org.hibernate.FetchMode

@Slf4j
class UrlMappings {

	static mappings = { ctx ->
		def grailsApplication = grails.util.Holders.grailsApplication
		def defaultSpudPage = grailsApplication?.config?.spud?.cms?.defaultPage ?: 'home'
		println "defaultSpudPage: ${defaultSpudPage}"
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
						println "starts with forbidden returning false"
						return false
					}
					println "id: ${id}"
					def siteId = GrailsWebRequest.lookup().getAttribute('spudSiteId',0)
					println "siteId: ${siteId}"
					def urlName = id ?: defaultSpudPage
					println "urlName: ${urlName}"
					def page = SpudPage.withCriteria(readOnly:true, uniqueResult:true, cache:true) {
						eq('siteId',siteId)
						eq('published',true)
						eq('urlName', urlName)
						eq('published',true)
						fetchMode 'partials', FetchMode.JOIN
					}
					println "page: ${page}"
					if(!page) {
						println "page was null"
						return false
					} else {
						if(page.visibility == 1) {
							println "cacheEnabled set to false"
							cacheEnabled = false
						}
						println "returning true"
						return true
					}

				})
			}
		}

	}
}
