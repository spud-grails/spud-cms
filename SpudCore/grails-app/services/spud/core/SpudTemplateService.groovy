package spud.core

import grails.transaction.Transactional

@Transactional
class SpudTemplateService {
	def grailsApplication

  def activeTemplateService(name = 'system') {
    if(name) {
      return templateServiceByName(name)
    }

    return templateServiceByName('system')
  }

  private templateServiceByName(key) {
    def engineName = grailsApplication.config.spud.templateEngine[key]
    if(engineName) {
      return grailsApplication.mainContext[engineName]
    } else {
      return null
    }
  }
}
