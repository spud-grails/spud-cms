import spud.cms.*
import org.hibernate.FetchMode
class SpudPageUrlMappings {

	static mappings = { ctx ->
		def grailsApplication = grails.util.Holders.grailsApplication
		def defaultSpudPage = grailsApplication?.config?.spud?.cms?.defaultPage ?: 'home'

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
						return false
					}

					def siteId = org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest.lookup().getAttribute('spudSiteId',0)
					def urlName = id ?: defaultSpudPage
					def page = SpudPage.withCriteria(readOnly:true, uniqueResult:true, cache:true) {
						eq('siteId',siteId)
						eq('published',true)
						eq('urlName', urlName)
						eq('published',true)
						fetchMode 'partials', FetchMode.JOIN
					}
					if(!page) {
						return false
					} else {
						if(page.visibility == 1) {
							cacheEnabled = false
						}
						return true
					}

				})
			}
		}

	}
}
