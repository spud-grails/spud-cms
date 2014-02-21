package spud;
import org.springframework.web.context.request.RequestContextHolder
import spud.cms.*
class SpudCmsSitemap {
	static sitemap = "cms"


	public List getSitemapUrls() {
		def pages = SpudPage.withCriteria(readOnly:true, cache:true) {
			eq('siteId',0)
			eq('published', true)
			eq('visibility', 0)
		}

		return pages.collect { page ->
			[url: "${protocol}://${request.serverName}${request.serverPort == 80 ? '' : ':' + request.serverPort}${request.contextPath == '/' ? '/' : (request.contextPath)}/" + page.urlName, lastMod: page.lastUpdated]
		}
	}

	public String getDefaultChangeFrequency() {
		return "monthly"
	}

	public Double getDefaultPriority() {
		return 1.0
	}

	def getProtocol() {
		if(request.protocol.contains('HTTPS')) {
			return 'https'
		} else {
			return 'http'
		}
	}

	def getRequest() {
		RequestContextHolder.currentRequestAttributes().getRequest()
	}
}