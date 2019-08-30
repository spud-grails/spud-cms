package spud.admin

import spud.cms.*
import spud.core.*
import grails.transaction.Transactional

@SpudApp(name="Pages", thumbnail="spud/admin/pages_icon.png", order="0")
@SpudSecure(['PAGES'])
class PagesController {
	static namespace = 'spud_admin'

	def layoutParserService
	def spudPageService
	def sitemapService
	def grailsCacheAdminService
	def spudMultiSiteService

	def index() {
		log.debug "index params: ${params}"
		def pages = SpudPage.withCriteria(readOnly:true) {
			isNull('spudPage')
			eq('siteId',spudMultiSiteService.activeSite.siteId)
			order('pageOrder')
		}

		render view: '/spud/admin/pages/index', model:[pages: pages, pageCount: SpudPage.count()]
	}

	def show() {
		def page = loadPage()
		if(!page) {
			return
		}


		render template: '/spud/page/show', model: [page:page], layout: null
	}

	def create() {
		def page     = new SpudPage()
		def partials = newPartialsForLayout(grailsApplication.config.spud.cms.defaultLayout)

		render view: '/spud/admin/pages/create', model:[page: page, layouts: this.layoutsForSite(), partials: partials]
	}

	def save() {
		if(!params.page) {
			flash.error = "Page submission not specified"
			redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
			return
		}

		def page = new SpudPage(params.page)
		page.siteId = spudMultiSiteService.activeSite.siteId
		spudPageService.generateUrlName(page)

		params.partial.each { partial ->
			if(partial.key.indexOf(".") == -1) {
				def partialRecord = new SpudPagePartial(symbolName: partial.key, name: partial.value?.name, postContent: partial.value.postContent, format: partial.value.format ?: 'html')
				page.addToPartials(partialRecord)
			}
		}

		if(page.save(flush:true)) {
			sitemapService.evictCache()
			spudPageService.evictCache()
			redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
		} else {
			flash.error = "Error Saving Page"
			def partials = page.partials
			render view: '/spud/admin/pages/create', model:[page: page, layouts: this.layoutsForSite(), partials: partials]
		}

	}

	def edit() {
		log.debug "edit: ${params}"
		def page = loadPage()
		log.debug "edit: ${page?.dump()}"
		if(!page) {
			return
		}
		def partials = newPartialsForLayout(page.layout, page.partials)
		def rtn = [:]
		rtn.page = page
		rtn.layouts = this.layoutsForSite()
		rtn.partials = partials
		log.debug "edit rtn: ${rtn}"
//		render view: '/spud/admin/pages/edit', model: [page: page, layouts: this.layoutsForSite(), partials: partials]
		render view: '/spud/admin/pages/edit', model: rtn
	}

	def update() {
		def page = loadPage()
		if(!page) {
			return
		}
		bindData(page, params.page)
		spudPageService.generateUrlName(page)
		def partialsToDelete = []
		page.siteId = spudMultiSiteService.activeSite.siteId
		page.partials.each { partial ->
			def partialParam = params.partial.find { it.key == partial.symbolName }
			if(!partialParam || !partialParam.value.content) {
				partialsToDelete << partial
			}
		}

		partialsToDelete.each {
			page.removeFromPartials(it)
			it.delete()
		}

		params.partial.each { partial ->
			if(partial.key.indexOf(".") == -1) {
				def partialRecord = page.partials.find { it.symbolName == partial.key}
				if(!partialRecord) {
					if(partial.value.postContent) {
						partialRecord = new SpudPagePartial(symbolName: partial.key, name: partial.value?.name, postContent: partial.value.postContent, format: partial.value.format ?: 'html')
						page.addToPartials(partialRecord)
					}
				} else {
					partialRecord.postContent = partial.value.postContent
					if(partial.value.format) {
						partialRecord.format = partial.value.format
					}
					partialRecord.save(flush:true)
				}
			}
		}


		if(page.save(flush:true)) {
			sitemapService.evictCache()
			spudPageService.evictCache()
			redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
		} else {
			render view: '/spud/admin/pages/edit', model: [page: page, layouts: this.layoutsForSite(), partials: page.partials]
		}
	}

	def delete() {
		def page = loadPage()
		if(!page) {
			return
		}
		spudPageService.remove(page)
		spudPageService.evictCache()
		sitemapService.evictCache()
		redirect resource: 'pages', action: 'index', namespace: 'spud_admin'

	}

	def clear() {
		spudPageService.evictCache()
		sitemapService.evictCache()
		grailsCacheAdminService.clearBlocksCache()
		flash.notice = "Page Cache Cleared Successfully!"
		redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
	}


	def pageParts() {
		def layoutName = params.template ?: grailsApplication.config.spud.cms.defaultLayout ?: 'page'
		def layouts = layoutsForSite()
		def layout = layouts.find { it.layout == layoutName }
		if(!layout) {
			layout = layouts[0]
		}
		if(layout) {
			def page
			if(params.id) {
				page = SpudPage.read(params.id)
			}
			if(!page) {
				page = new SpudPage()
			}

			def oldPagePartials = page.partials
			def newPagePartials = []
			layout.html.each { partial ->
				newPagePartials << new SpudPagePartial(symbolName: partial.parameterize(), name: partial)
			}

			newPagePartials.each { partial ->
				def oldPartial = oldPagePartials.find { partial.symbolName == it.symbolName}
				if(oldPartial) {
					partial.content = oldPartial.content
					partial.format  = oldPartial.format
				}
			}
			render template: "/spud/admin/pages/page_partials_form", model: [partials: newPagePartials, removePartials: oldPagePartials]
		}
	}

	private layoutsForSite() {
		return layoutParserService.layoutsForSite(spudMultiSiteService.activeSite.siteId)
	}

	private newPartialsForLayout(layoutName=null, existingPartials=null) {
		log.debug "newPartialsForLayout layoutName: ${layoutName} existingPartials: ${existingPartials}"
		def layoutsForSite  = layoutsForSite()
		log.debug "newPartialsForLayout layoutsForSite: ${layoutsForSite}"
		def defaultLayoutName = grailsApplication.config.spud.cms.defaultLayout ?: 'page'
		log.debug "newPartialsForLayout defaultLayoutName: ${defaultLayoutName}"
		if(!layoutName) {
			layoutName = defaultLayoutName
		}

		def layout = layoutsForSite.find { it.layout == layoutName}
		log.debug "newPartialsForLayout layout: ${layout}"
		if(!layout) {
			layout = layoutsForSite[0]
		}

		def partials = []
		if(existingPartials) {
			partials += existingPartials
		}
		log.debug "newPartialsForLayout partials: ${partials}"
		if(layout) {
			layout.html.each {
				if(!partials.find{ep -> ep.symbolName == it.parameterize()}) {
					partials << new SpudPagePartial(symbolName: it.parameterize(), name: it, content: null)
				}

				// Sort Partials to line up with template
				partials.sort { a ->
					layout.html.findIndexOf{ html -> html.parameterize() == a.symbolName}
				}
			}
		}
		partials.each { it ->
			log.debug "newPartialsForLayout it: ${it.dump()}"
		}
		return partials
	}

	private loadPage() {
		if(!params.id) {
			flash.error = "Page Submission not specified"
			redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
			return null
		}

		def page = SpudPage.findBySiteIdAndId(spudMultiSiteService.activeSite.siteId, params.long('id'), [readOnly:true])
		if(!page) {
			flash.error = "Page not found!"
			redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
			return null
		}
		return page
	}


}
