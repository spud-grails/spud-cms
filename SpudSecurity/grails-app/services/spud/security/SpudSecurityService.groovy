package spud.security

import org.springframework.security.web.savedrequest.DefaultSavedRequest
import org.springframework.security.web.WebAttributes
import java.lang.reflect.Field

class SpudSecurityService extends spud.core.AbstractSpudSecurityService {
	static transactional = false
	def springSecurityService

	def storeLocation(request) {
		def savedRequest = new DefaultSavedRequest(request,new org.springframework.security.web.PortResolverImpl())
    try {
       Field f = DefaultSavedRequest.getDeclaredField('requestURI')
       f.accessible = true
       f.set savedRequest, request.getForwardURI()
    }
    catch (ex) {
       log.error "Error assigning request to spring security for auth success!"
    }
		request.session.setAttribute(WebAttributes.SAVED_REQUEST, savedRequest);
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
		def userCount = SpudUser.count()
		if(userCount == 0) {
			return [controller: "setup", namespace: "spud_admin", action: "create"]
		}
		return [controller: 'login', action: 'auth']
	}

	def getLogoutUrl() {
		return [controller: 'logout', action: 'index']
	}
}

