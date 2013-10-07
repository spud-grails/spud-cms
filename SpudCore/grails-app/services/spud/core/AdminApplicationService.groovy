package spud.core

import grails.transaction.Transactional

@Transactional
class AdminApplicationService {
	def grailsApplication
	def initialize() {
		def adminApplications = []

		grailsApplication.controllerClasses.each { controllerClass ->
			def annotation = controllerClass.clazz.getAnnotation(spud.core.SpudApp)
			if(annotation && annotation.subsection() == 'false') {
				adminApplications << adminMapFromAnnotation(annotation, controllerClass)
			}
		}

		grailsApplication.config.spud.core.adminApplications = adminApplications
	}

	private def adminMapFromAnnotation(annotation, controllerClass) {
		def rtn       = [:]
		rtn.name      = annotation.name()
		rtn.thumbnail = annotation.thumbnail()
		rtn.order     = annotation.order().toInteger()
		rtn.url       = [controller: controllerClass.logicalPropertyName, action: 'index']
		return rtn
	}
}
