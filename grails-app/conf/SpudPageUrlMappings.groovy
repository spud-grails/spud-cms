import spud.cms.*
class SpudPageUrlMappings {

	static mappings = {
		def grailsApplication = getGrailsApplication()
		def defaultSpudPage = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		def FORBIDDEN = [
			'plugins',
			'WEB-INF',
			'assets',
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
			constraints {
				id(validator: { id ->
					if(FORBIDDEN.find{ forbidden -> id?.startsWith(forbidden)}) {
						return false
					}
					//TODO : Perhaps store this in the request
					if(!id) {
						println "Id Not Detected, Looking for ${defaultSpudPage} ${ SpudPage.findByUrlName(defaultSpudPage,[cache:true, readOnly:true]) ? true : false}"
						return SpudPage.findByUrlName(defaultSpudPage,[cache:true, readOnly:true]) ? true : false
					} else {
						return SpudPage.findByUrlName(id,[cache:true, readOnly:true]) ? true : false
					}

				})
			}
		}

	}
}
