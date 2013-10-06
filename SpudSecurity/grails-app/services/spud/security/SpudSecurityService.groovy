package spud.security

import grails.transaction.Transactional

@Transactional
class SpudSecurityService extends spud.core.AbstractSpudSecurityService {
	def springSecurityService

	def storeLocation(request) {
		log.warn("Store Location (request) not implemented")
	}

	def isAuthorized(spudSecureAnnotation, request, params) {
		if(!springSecurityService.isLoggedIn()) {
			return false
		}

		if(spudSecureAnnotation.value.contains('AUTHORIZED')) {
			return true
		}

		return false
	}

	def getLoginUrl() {
		log.error "SECURITY SERVICE NOT IMPLEMENTED (getLoginUrl)"
		return [controller: 'login', action: 'auth']
	}
}

