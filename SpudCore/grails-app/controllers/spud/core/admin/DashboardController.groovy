package spud.core.admin
import  spud.core.*


@SpudSecure(['AUTHORIZED'])
class DashboardController {

		static namespace = 'spud_admin'

    def index = {
    	render view: '/spud/admin/dashboard/index', model: [adminApplications: grailsApplication.config.spud.core.adminApplications, breadCrumbs:[["Dashboard", "/spud/admin"]]]
    }
}
