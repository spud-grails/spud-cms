class SpudPermalinksUrlMappings {

	static mappings = {
    "/spud/admin/permalinks"(resources: 'permalinks', namespace: 'spud_admin')
    "/spud/admin/permalinks/$id/delete"(controller: 'permalinks', action: 'delete', method: 'POST', namespace: 'spud_admin')
	}
}
