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
/*
		params.partial.each { partial ->
			if(partial.key.indexOf(".") == -1) {
				def partialRecord = new SpudPagePartial(symbolName: partial.key, name: partial.value?.name, postContent: partial.value.postContent, format: partial.value.format ?: 'html')
				page.addToPartials(partialRecord)
			}
		}
*/
		def item = params.partial.find { it -> it.key.indexOf(".") == -1 }
		log.debug "update item: ${item?.dump()}"
		def symbolName = item.key
		def name = item.value

		def postContent = params.partial.get("${symbolName}.postContent")
		def format = params.partial.get("${symbolName}.format")

		log.debug "update postContent: ${postContent}"
//						if(partial.value.postContent) {
		def partialRecord = new SpudPagePartial(symbolName: symbolName, name: name, postContent: postContent, format: format ?: 'html')
		page.addToPartials(partialRecord)

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
		log.debug "update params: ${params}"
		def page = loadPage()
		if(!page) {
			return
		}

		try {
			log.debug "update page: ${page}"
			bindData(page, params.page)
			spudPageService.generateUrlName(page)
			def partialsToDelete = []
			page.siteId = spudMultiSiteService.activeSite.siteId
			page.partials.each { partial ->
				log.debug "update partial: ${partial}"
				log.debug "update partial.getClass().simpleName: ${partial.getClass().simpleName}"
				debugPartial(partial)
//				log.debug "update partial.key: ${partial.key}"
				def partialParam = params.partial.find { it.key == partial.symbolName }
				debugPartial(partialParam)
				log.debug "update partialParam: ${partialParam}"
				log.debug "update partialParam.getClass().simpleName: ${partialParam.getClass().simpleName}"
				log.trace "update partialParam?.dump(): ${partialParam?.dump()}"
//				if(!partialParam || !partialParam.value?.content) {
//				if(!partialParam || !partialParam.content) {
				if(!partialParam) {
					partialsToDelete << partial
				}
			}

			partialsToDelete.each {
				page.removeFromPartials(it)
				it.delete()
			}

			log.debug "update params.partial.size(): ${params.partial.size()}"

			params.partial.each { partial ->
				debugPartial(partial)
			}

//			def item = params.partials.find { key, value -> key.indexOf(".") == -1 }
			log.debug "update params.partial.getClass().simpleName: ${params.partial.getClass().simpleName}"
			def item = params.partial.find { it -> it.key.indexOf(".") == -1 }
			log.debug "update item: ${item?.dump()}"
			def symbolName = item.key
			log.debug "update symbolName: ${symbolName}"
			def name = item.value
			log.debug "update name: ${name}"

			def postContentKey = params.partial."${symbolName}.postContent"
			log.debug "update postContentKey: ${postContentKey}"
			def postContent = postContentKey
			log.debug "update postContent: ${postContent}"
			def formatKey = params.partial."${symbolName}.format"
			def format = formatKey
			log.debug "update format: ${format}"

			def partialRecord = page.partials.find { it.symbolName == symbolName}
			log.debug "update partialRecord.getClass().simpleName: ${partialRecord.getClass().simpleName}"
			debugPartial(partialRecord)
			if(!partialRecord) {
				log.debug "update partialRecord was null"
				log.debug "update postContent: ${postContent}"
//						if(partial.value.postContent) {
				if(postContent) {
//							partialRecord = new SpudPagePartial(symbolName: partial.key, name: partial.value?.name, postContent: partial.value.postContent, format: partial.value.format ?: 'html')
					partialRecord = new SpudPagePartial(symbolName: symbolName, name: name, postContent: postContent, format: format ?: 'html')
					page.addToPartials(partialRecord)
				}
			} else {
				log.debug "update partialRecord was not null"
//						partialRecord.postContent = partial.postContent
				if(postContent) {
					log.debug "update postContent: ${postContent}"
					log.trace "update partialRecord.postContent: ${partialRecord.postContent}"
					partialRecord.postContent = postContent
				}
				if(format) {
					//						if(partial.value.format) {
					log.debug "update format: ${format}"
					//							partialRecord.format = partial.value.format
					partialRecord.format = format
				}
				partialRecord.save(flush:true)
			}
/*			
			log.debug "update params.partial: ${params.partial}"
			params.partial.each { partial ->
				log.debug "update partial: ${partial}"
				log.debug "update partial.key.indexOf('.'): ${partial.key.indexOf(".")}"
				if(partial.key.indexOf(".") == -1) {
					log.debug "update processing partial.key: ${partial.key}"
					debugPartial(partial)
					def partialRecord = page.partials.find { it.symbolName == partial.key}
					log.debug "update partialRecord.getClass().simpleName: ${partialRecord.getClass().simpleName}"
					debugPartial(partialRecord)
					if(!partialRecord) {
						log.debug "update partialRecord was null"
						log.debug "update partial.postContent: ${partial.postContent}"
//						if(partial.value.postContent) {
						if(partial.postContent) {
//							partialRecord = new SpudPagePartial(symbolName: partial.key, name: partial.value?.name, postContent: partial.value.postContent, format: partial.value.format ?: 'html')
							partialRecord = new SpudPagePartial(symbolName: partial.key, name: partial.name, postContent: partial.postContent, format: partial.format ?: 'html')
							page.addToPartials(partialRecord)
						}
					} else {
						log.debug "update partialRecord was not null"
//						partialRecord.postContent = partial.postContent
						debugPartial(partial)
						if(partial.postContent) {
							log.debug "update partial.value: ${partial.postContent}"
							log.trace "update partialRecord.postContent: ${partialRecord.postContent}"
							partialRecord.postContent = partial.postContent
						}
						if(partial instanceof SpudPagePartial) {
							if(partial.format) {
	//						if(partial.value.format) {
								log.debug "update partial.format: ${partial.format}"
	//							partialRecord.format = partial.value.format
								partialRecord.format = partial.format
							}
							partialRecord.save(flush:true)
						} else {
							log.debug "update partial not instanceof SpudPagePartial: ${partial.getClass().simpleName}"
						}
					}
				} else {
					log.debug "update ignoring partial.key: ${partial.key}"
					debugPartial(partial)
				}
			}
*/
			if(!page.validate()) {
				page.errors.each { error ->
					log.error "update error saving page: ${error}"
				}
			}
			if(page.save(flush:true)) {
				sitemapService.evictCache()
				spudPageService.evictCache()
				redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
			} else {
				render view: '/spud/admin/pages/edit', model: [page: page, layouts: this.layoutsForSite(), partials: page.partials]
			}
		} catch(e) {
			log.error "An error occurred attempting to update the spud page. Reason: ${e.message}", e
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
			log.trace "newPartialsForLayout it: ${it.dump()}"
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

	private debugPartial(partial) {
		log.debug "=========================================================================================================================================================================="
		log.debug "debugPartial partial.getClass().simpleName: ${partial.getClass().simpleName}"
		if(partial instanceof String) {
			log.debug "debugPartial String: ${partial}"
		} else if(partial instanceof SpudPagePartial) {
			log.debug "debugPartial SpudPagePartial format: ${partial.format}"
			log.debug "debugPartial SpudPagePartial name: ${partial.name}"
			log.debug "debugPartial SpudPagePartial pageId: ${partial.pageId}"
			log.debug "debugPartial SpudPagePartial symbolName: ${partial.symbolName}"
			log.debug "debugPartial SpudPagePartial postContent: ${partial.postContent}"
		} else if(partial.getClass().simpleName == "Entry") {
			log.debug "debugPartial Entry key: ${partial.key}"
			log.debug "debugPartial Entry value: ${partial.value}"
			log.debug "debugPartial partial.getClass().simpleName: ${partial.key.getClass().simpleName}"
			log.debug "debugPartial partial.getClass().simpleName: ${partial.value.getClass().simpleName}"
		} else {
			log.debug "debugPartial partial.getClass().getCanonicalName(): ${partial.getClass().getCanonicalName()}"
		}
		log.debug "=========================================================================================================================================================================="
	}
}
