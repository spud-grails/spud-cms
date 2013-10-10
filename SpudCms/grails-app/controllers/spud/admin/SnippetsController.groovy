package spud.admin
import  spud.cms.*
import  spud.core.*

@SpudApp(name="Snippets", thumbnail="spud/admin/snippets_thumb.png")
@SpudSecure(['SNIPPETS'])
class SnippetsController {
	static namespace = "spud_admin"

	def index = {
		def snippets = SpudSnippet.list([sort: 'name'] + params)
		render view: '/spud/admin/snippets/index', model:[snippets: snippets, snippetCount: SpudSnippet.count()]
	}

	def show = {
		def snippet = loadSnippet()
		if(!snippet) {
			return
		}
	}

	def create = {

	}

	def save = {

	}

	def edit = {
		def snippet = loadSnippet()
		if(!snippet) {
			return
		}
	}

	def update = {
		def snippet = loadSnippet()
		if(!snippet) {
			return
		}
	}

	def delete = {
		def snippet = loadSnippet()
		if(!snippet) {
			return
		}
	}

	private loadSnippet() {
  	if(!params.id) {
			flash.error = "Snippet Id Not Specified"
			redirect controller: 'snippets', action: 'index', namespace: 'spud_admin'
			return null
		}

		def page = SpudSnippet.get(params.id)
		if(!page) {
			flash.error = "Snippet not found!"
			redirect controller: 'snippets', action: 'index', namespace: 'spud_admin'
			return null
		}
		return page
	}
}
