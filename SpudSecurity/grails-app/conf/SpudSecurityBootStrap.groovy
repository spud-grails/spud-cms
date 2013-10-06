import spud.security.*

class SpudSecurityBootStrap {

    def init = { servletContext ->
    	def adminRole = SpudRole.findByAuthority('SUPER_ADMIN')
    	if(!adminRole) {
    		adminRole = new SpudRole(authority: 'SUPER_ADMIN').save(flush: true)
    	}
    }
    def destroy = {
    }
}
