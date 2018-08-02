package spud.cms

import org.hibernate.FetchMode
class SpudCmsTagLib {
  static defaultEncodeAs = 'html'
	static encodeAsForTags = [menu: 'raw', pages: 'raw', pageLink: 'raw', snippet: 'raw', applyLayout: 'raw']
	static namespace = 'sp'

	def spudTemplateService
    def spudMultiSiteService

	def menu = { attrs, body ->
		def htmlArgs = attrs.findAll { !(['name','activeClass','maxDepth', 'linkOptions'].contains(it.key))}

		def defaultPage = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		if(!attrs.name) {
			 throw new IllegalStateException("Property [name] must be set!")
		}
		def maxDepth = attrs.maxDepth ?: 0
		def activeClass = attrs.activeClass ?: 'menu-active'
		def siteId = spudMultiSiteService.siteForUrl().siteId

		def menuId = SpudMenu.withCriteria(uniqueResult:true, cache:true,readOnly:true) {
			eq('siteId',siteId)
			eq('name', attrs.name)
			projections {
				property('id')
			}
		}
		// def menu = SpudMenu.findBySiteIdAndName(siteId, attrs.name,[cache:true,readOnly:true])
		if(!menuId) {
			return
		}


		out << "<ul ${paramsToHtmlAttr(htmlArgs)}>"

		def menuItems = SpudMenuItem.withCriteria(readOnly:true, cache:true) {
			eq('menu.id', menuId)
			fetchMode 'page', FetchMode.JOIN
			order('parentType','asc')
			order('parentId','asc')
			order('menuOrder','asc')
		}

		def groupedMenuItems = menuItems.groupBy{it.parentType}
		def parentItems = groupedMenuItems['SpudMenu']
		def childItems  = groupedMenuItems['SpudMenuItem'] ? groupedMenuItems['SpudMenuItem'].groupBy{it.parentId} : [:]
		// println parentItems
		parentItems?.each { item ->
			def active = false
			def linkOptions = attrs.linkOptions ?: [:]
			if(item.urlName) {
				linkOptions += [controller: "spudPage", action: "show"]
				if(item.urlName != defaultPage) {
					linkOptions.id = item.urlName
				}
				active = isCurrentUrl("/" + item.urlName)
				if(item.urlName == defaultPage && isCurrentUrl("/")) {
					active = true
				}
			} else if(item.url) {
				active = isCurrentUrl(item.url)
				linkOptions.url = item.url
			}
			def classes = item.classes ? item.classes.split(" ") : []
			if(active) {
				classes += activeClass
			}
			if(classes) {
				linkOptions.class = classes.join(" ")
			}

			def linkTag = g.link(linkOptions, item.name)
			out << "<li ${classes ? paramsToHtmlAttr([class: classes.join(" ")]) : ''}>"
			out << linkTag
			if(maxDepth == 0 || maxDepth > 1){
				out  << listMenuItem(childItems, item.id, 2, attrs)
			}
			out << "</li>"
		}
		out << "</ul>"
	}

	def pages = {attrs, body ->
		// TODO: Build this soon please
	}

	def createPageLink = {attrs ->
		def link = g.createLink(buildPageLinkOptions(attrs))
		out << link
	}

	def pageLink = {attrs, body ->
		def link = g.link(buildPageLinkOptions(attrs), body())
		out << link
	}

	def snippet = { attrs ->
		if(!attrs.name) {
			 throw new IllegalStateException("Property [name] must be set!")
		}
		def siteId = spudMultiSiteService.siteForUrl().siteId
		def snippet = SpudSnippet.findBySiteIdAndName(siteId,attrs.name)
		if(snippet) {
			out << snippet.render()
		}
	}

	def applyLayout = { attrs,body ->
		out << g.applyLayout(attrs,body)
	}

	private listMenuItem(childItems, itemId, depth, attrs) {
		def maxDepth = attrs.maxDepth ?: 0
		def defaultPage = grailsApplication.config.spud.cms.defaultPage ?: 'home'
		def activeClass = attrs.activeClass ?: "menu-active"
		def menuItems = childItems.get(itemId)
		if(!menuItems) {
			return ""
		}
		def content = "<ul>"
		menuItems?.sort{it.menuOrder}?.each { item ->
			def active = false
			def linkOptions = attrs.linkOptions ?: [:]
			if(item.urlName) {
				linkOptions += [controller: "spudPage", action: "show"]
				if(item.urlName != defaultPage) {
					linkOptions.id = item.urlName
				}
				active = isCurrentUrl("/" + item.urlName)
				if(item.urlName == defaultPage && isCurrentUrl("/")) {
					active = true
				}
			} else if(item.url) {
				active = isCurrentUrl(item.url)
				linkOptions.url = item.url
			}
			def classes = item.classes ? item.classes.split(" ") : []
			if(active) {
				classes += activeClass
			}
			linkOptions.class = classes.join(" ")

			def linkTag = g.link(linkOptions, item.name)
			content += "<li ${paramsToHtmlAttr([class: classes.join(" ")])}>"
			content += linkTag
			if(maxDepth == 0 || maxDepth > depth) {
				content  += listMenuItem(childItems, item.id, depth + 1, attrs)
			}
			content += "</li>"

		}
		content += "</ul>"
		return content
	}

	private isCurrentUrl(url) {
		def currentUrl = request.forwardURI
		if(request.contextPath != "/") {
			currentUrl = currentUrl.substring(request.contextPath.size())
		}
		if(currentUrl.indexOf(url) == 0) {
			if(currentUrl.size() > url.size()) {
				def remainingUrl = currentUrl.substring(url.size())
				if(remainingUrl.startsWith("/")) {
					return true
				}
			} else {
				return true
			}
		}
		return false
	}

	private paramsToHtmlAttr(attrs) {
		attrs.collect { key, value -> "${key}=\"${value.replace('\'', '\\\'')}\"" }?.join(" ")
	}

	private buildPageLinkOptions(attrs) {
		def name = attrs.remove('name')

		if(!name) {
			throw new IllegalArgumentException("'name' is required")
		}

		def siteId = (attrs.remove('siteId') ?: 0) as Integer
 
		def page = SpudPage.createCriteria().get {
			eq('name', name)
			eq('siteId', siteId)
		}

		def linkOptions = attrs.remove('linkOptions') ?: [:]

		linkOptions += [controller: "spudPage", action: "show"]
		linkOptions.id = page?.urlName ?: ''
		linkOptions += attrs

		linkOptions
	}
}