package spud.cms

import org.hibernate.FetchMode


class SpudCmsInterceptor {

	SpudCmsInterceptor() {
		match(controller:"page")
	}

    boolean before() {
		log.debug "SpudCmsInterceptor before"
		def pageUri = request.forwardURI
		if(request.contextPath && request.contextPath != "/") {
			pageUri = pageUri.substring(request.contextPath.size())
		}
		if(pageUri.startsWith("/")) {
			if(pageUri.size() > 1) {
				pageUri = pageUri.substring(1)
			} else {
				pageUri = null
			}
		}

		if(!pageUri) {
			pageUri = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		}
		def siteId = 0
		// TODO Determine Site id for Multisite
		def page = SpudPage.withCriteria(readOnly:true, uniqueResult: true, cache:true) {
			eq('siteId', siteId)
			eq('urlName',pageUri)
			fetchMode 'partials', FetchMode.JOIN
		}
		log.debug "page: ${page}"
		println "page: ${page}"
		if(page) {
			request.setAttribute('spudPage',page)
		} else {
			def file = grailsApplication.parentContext.getResource("${pageUri}")
			if (!file.exists()) {
				return
			} else {
				def format = servletContext.getMimeType(request.forwardURI)
				response.setContentType(format)
				response.outputStream << file.inputStream.getBytes()
				return false
			}
		}
	}

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
