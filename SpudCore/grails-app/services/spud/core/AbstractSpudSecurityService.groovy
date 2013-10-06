package spud.core

import grails.transaction.Transactional

@Transactional
class AbstractSpudSecurityService {

	def getCurrentUser() {
		log.warn("SECURITY SERVICE NOT IMPLEMENTED")
		return null
	}

	def getCurrentUserDisplayName() {
		log.warn("SECURITY SERVICE NOT IMPLEMENTED")
		return "Unknown"
	}

	def isLoggedIn() {
		log.warn("SECURITY SERVICE NOT IMPLEMENTED")
		return false
	}

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

	def getLogoutUrl() {
		log.error "SECURITY SERVICE NOT IMPLEMENTED (getLogoutUrl)"
		return [controller: 'session', action: 'logout']
	}
}
