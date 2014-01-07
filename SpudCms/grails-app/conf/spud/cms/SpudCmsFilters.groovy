package spud.cms
import org.codehaus.groovy.grails.web.mapping.*;
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest;
class SpudCmsFilters {
    def grailsApplication
    def servletContext
    def filters = {
        all(controller: 'page') {
            before = {

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
                def page = SpudPage.findBySiteIdAndUrlName(siteId,pageUri)
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
        }
    }
}
