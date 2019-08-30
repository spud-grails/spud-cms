package spud.admin

import spud.cms.*
import spud.core.*

@SpudApp(name="Snippets", thumbnail="spud/admin/snippets_icon.png", order="1")
@SpudSecure(['SNIPPETS'])
class SnippetsController {
	static namespace = "spud_admin"
	def spudPageService
	def spudMultiSiteService

	def index = {
		def snippets = SpudSnippet.createCriteria().list([sort: 'name', max:25] + params) {
			eq('siteId',spudMultiSiteService.activeSite.siteId)
		}
		// def snippets = SpudSnippet.list()
		render view: '/spud/admin/snippets/index', model:[snippets: snippets, snippetCount: SpudSnippet.count()]
	}

	def show = {
		def snippet = loadSnippet()
		if(!snippet) {
			return
		}
	}

	def create = {
		def snippet = new SpudSnippet()
		render view: '/spud/admin/snippets/create', model:[snippet: snippet]
	}

	def save = {
    if(!params.snippet) {
      flash.error = "Snippet submission not specified"
      redirect resource: 'snippets', action: 'index', namespace: 'spud_admin'
      return
    }

    def snippet = new SpudSnippet(params.snippet)

    snippet.siteId = spudMultiSiteService.activeSite.siteId

    if(snippet.save(flush:true)) {
		spudPageService.evictCache()
		redirect resource: 'snippets', action: 'index', namespace: 'spud_admin'
    } else {
      flash.error = "Error Saving Snippet"
      render view: '/spud/admin/snippets/create', model:[snippet:snippet]
    }
	}

	def edit = {
		def snippet = loadSnippet()
		if(!snippet) {
			return
		}
    render view: '/spud/admin/snippets/edit', model: [snippet: snippet]

	}

	def update = {
		def snippet = loadSnippet()
		if(!snippet) {
			return
		}
		bindData(snippet,params.snippet)
		snippet.siteId = spudMultiSiteService.activeSite.siteId

		if(snippet.save(flush:true)) {
			spudPageService.evictCache()
			redirect resource: 'snippets', action: 'index', namespace: 'spud_admin'
		} else {
			render view: '/spud/admin/snippets/edit', model: [snippet: snippet]
		}
	}

	def delete = {
		def snippet = loadSnippet()
		if(!snippet) {
			return
		}
		snippet.delete()
		spudPageService.evictCache()
		flash.notice = "Snippet Removed Successfully!"
		redirect resource: 'snippets', action: 'index', namespace: 'spud_admin'
	}

	private loadSnippet() {
  	if(!params.id) {
			flash.error = "Snippet Id Not Specified"
			redirect controller: 'snippets', action: 'index', namespace: 'spud_admin'
			return null
		}

		def page = SpudSnippet.findBySiteIdAndId(spudMultiSiteService.activeSite.siteId, params.long('id'))
		if(!page) {
			flash.error = "Snippet not found!"
			redirect controller: 'snippets', action: 'index', namespace: 'spud_admin'
			return null
		}
		return page
	}
}
