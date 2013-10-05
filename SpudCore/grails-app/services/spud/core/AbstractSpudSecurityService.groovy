package spud.core

import grails.transaction.Transactional

@Transactional
class AbstractSpudSecurityService {

	def storeLocation(request) {
		log.warn("Store Location (request) not implemented")
	}

	def isAuthorized(spudSecureAnnotation, request, params) {
		log.error "SECURITY SERVICE NOT IMPLEMENTED (isAuthorized)"
		return true
	}

	def getLoginUrl() {
		log.error "SECURITY SERVICE NOT IMPLEMENTED (getLoginUrl)"
		return [controller: 'session', action: 'login']
	}
}
