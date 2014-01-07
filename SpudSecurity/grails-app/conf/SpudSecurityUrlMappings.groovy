class SpudSecurityUrlMappings {

	static mappings = {

		"/spud/admin/users"(resources: 'user', namespace: 'spud_admin')
		"/spud/admin/users/$id/delete"(controller: 'user', action: 'delete', method: 'POST', namespace: 'spud_admin')
		"/spud/setup"(controller: "setup", namespace: "spud_admin", action: "create", method: "GET")
		"/spud/setup"(controller: "setup", namespace: "spud_admin", action: "save", method: "POST")

	}
}
