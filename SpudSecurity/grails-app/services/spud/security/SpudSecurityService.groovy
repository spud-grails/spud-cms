package spud.security

class SpudSecurityService extends spud.core.AbstractSpudSecurityService {
	static transactional = false
	def springSecurityService

	def storeLocation(request) {
		request.session.setAttribute('spudSecurityAuthSuccessTargetUrl', request.forwardURI)
		// request.requestURI = request.forwardURI
		// def savedRequest = new GrailsSavedRequest(request,new org.springframework.security.web.PortResolverImpl())
		// println savedRequest.getRedirectUrl()
		// request.session.setAttribute(WebAttributes.SAVED_REQUEST, savedRequest);
	}

	def getCurrentUser() {
		return springSecurityService.currentUser
	}

	def getCurrentUserDisplayName() {
		return springSecurityService.currentUser.displayName
	}

	def isLoggedIn() {
		return springSecurityService.isLoggedIn()
	}

	def isAuthorized(spudSecureAnnotation, request, params) {
		if(!springSecurityService.isLoggedIn()) {
			return false
		}

		if(spudSecureAnnotation.value().contains('AUTHORIZED')) {
			return true
		}
		if(getCurrentUser().superAdmin) {
			return true
		}
		def requiredAuthorities = spudSecureAnnotation.value()
		def authorities = getCurrentUser().authorities
		def authorized = false
		authorities.find { role ->
			def authority = role.authority
			if(requiredAuthorities.find { it == authority}) {
				authorized = true
				return true
			}
		}

		return authorized
	}

	def getLoginUrl() {
		return [controller: 'login', action: 'auth']
	}

	def getLogoutUrl() {
		return [controller: 'logout', action: 'index']
	}
}

