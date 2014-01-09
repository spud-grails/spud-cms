class SpudPageUrlMappings {

	static mappings = {



		"/spud/admin/pages"(resources: "pages", namespace: "spud_admin")
		"/spud/admin/pages/page_parts/$id"(controller: "pages", namespace: "spud_admin", action: 'pageParts')
		"/spud/admin/snippets"(resources: "snippets", namespace: "spud_admin")
		"/spud/admin/menus"(resources: "menus", namespace: "spud_admin")
		"/spud/admin/menus"(resources: "menus", namespace: "spud_admin") {
			"/menu_items"(resources: "menuItems", namespace: "spud_admin")
		}

		"/$id**" (
			controller: 'page',
			action: 'show',
			format: 'html'
		)
	}
}
