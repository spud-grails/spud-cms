package spud.core.admin
import  spud.core.*


@SpudSecure(['AUTHORIZED'])
class DashboardController {

		static namespace = 'spud_admin'

    def index = {
    	[adminApplications: grailsApplication.config.spud.core.adminApplications, breadCrumbs:[["Dashboard", "/spud/admin"]]]
    }
}
