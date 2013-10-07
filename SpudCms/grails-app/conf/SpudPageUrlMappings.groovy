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




		"/spud/admin/pages/create"(controller: 'pages', namespace: 'spud_admin', action: 'create')
		"/spud/admin/pages/$id/edit"(controller: 'pages', namespace: 'spud_admin', action: 'edit')
		"/spud/admin/pages/$id/delete"(controller: 'pages', namespace: 'spud_admin', action: 'delete')
		"/spud/admin/pages/$id"(controller: 'pages', namespace: 'spud_admin', action:'show')
		"/spud/admin/pages/$id"(
			controller: 'pages',
			namespace: 'spud_admin',
			action: [PUT: 'update', DELETE: 'delete']
		)
	}
}
