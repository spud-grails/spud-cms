package spud.cms
import org.hibernate.FetchMode
class SpudCmsAdminTagLib {
  static defaultEncodeAs = 'html'
    static encodeAsForTags = [pageSelect: 'raw']
	static namespace = 'spAdmin'

	def grailsApplication
	def spudPageService

	def pageSelect = { attrs, body ->
		def filter = attrs.remove('filter')
		def pageOptions = spudPageService.optionsTreeForPage(filter ? [filter: filter] : [:])
		out << g.select(attrs + [from: pageOptions, optionKey: 'value', optionValue: 'name'])
	}
}

