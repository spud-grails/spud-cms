class SpudPageUrlMappings {

	static mappings = {


		"/spud/admin/pages"(
			controller: 'pages',
			namespace: 'spud_admin',
			action: 'index'
		)

		"/spud/admin/pages"(
			controller: 'pages',
			namespace: 'spud_admin',
			action: [GET:'index', POST: 'save']
		)



		"/spud/admin/pages"(resources: "pages", namespace: "spud_admin")
		"/spud/admin/pages/$id/delete"(controller: "pages", action: "delete", method: "POST", namespace: "spud_admin")

		"/$id**" (
			controller: 'page',
			action: 'show',
			format: 'html'
		)
	}
}
