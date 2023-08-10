package spud.cms

import org.hibernate.FetchMode

class SpudCmsAdminTagLib {
  static defaultEncodeAs = 'html'
    static encodeAsForTags = [pageSelect: 'raw', menuItemSelect: 'raw']
	static namespace = 'spAdmin'

	def spudPageService
	def spudMenuService
    def spudMultiSiteService

	def pageSelect = { attrs, body ->
		def filter = attrs.remove('filter')
		def pageOptions = spudPageService.optionsTreeForPage((filter ? [filter: filter] : [:]) + [siteId: spudMultiSiteService.activeSite.siteId])
		out << g.select(attrs + [from: pageOptions, optionKey: 'value', optionValue: 'name'])
	}

	def cacheBlock = {attrs, body ->
		if(grailsApplication.config.spud.cms.cacheEnabled && grailsApplication.config.spud.cms.cacheMode == 'partial') {
			out << cache.block(attrs,body)
		} else {
			out << body()
		}

	}


	def menuItemSelect = { attrs, body ->
		if(!attrs.menu) {
		 throw new IllegalStateException("Property [menu] must be set!")
		}
		def filter = attrs.remove('filter')
		def menu   = attrs.remove('menu')
		def menuOptions = spudMenuService.optionsTreeForItem(menu,filter ? [filter: filter] : [:])

		out << g.select(attrs + [from: menuOptions, optionKey: 'value', optionValue: 'name', noSelection:['':menu.name]])
	}
}
