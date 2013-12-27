class SpudSecurityUrlMappings {

	static mappings = {
		"/spud/admin/users"(
			controller: 'user',
			namespace: 'spud_admin'
		) {
			action = [GET:'index', POST: 'save']
		}

		"/spud/admin/users"(resources: 'user', namespace: 'spud_admin')
		// "/spud/admin/users/create"(controller: 'user', namespace: 'spud_admin', action: 'create')
		// "/spud/admin/users/$id/edit"(controller: 'user', namespace: 'spud_admin', action: 'edit')
		// "/spud/admin/users/$id/delete"(controller: 'user', namespace: 'spud_admin', action: 'delete')
		// "/spud/admin/users/$id"(controller: 'user', namespace: 'spud_admin', action:'show')
		// "/spud/admin/users/$id"(
		// 	controller: 'user',
		// 	namespace: 'spud_admin',
		// 	action: [PUT: 'update', DELETE: 'delete']
		// )

	}
}
