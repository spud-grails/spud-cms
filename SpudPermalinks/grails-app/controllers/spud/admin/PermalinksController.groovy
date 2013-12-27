package spud.admin
import spud.permalinks.*
import  spud.core.*
import  spud.security.*

@SpudApp(name="Permalinks", thumbnail="spud/admin/permalinks_thumb.png")
@SpudSecure(['PERMALINKS'])
class PermalinksController {
	static namespace = 'spud_admin'

	def index = {
		def permalinks = SpudPermalink.list([max:25] + params)
		render view: '/spud/admin/permalinks/index', model:[permalinks: permalinks, permalinkCount: SpudPermalink.count()]
	}

	def create = {
		def permalink = new SpudPermalink()
		render view: '/spud/admin/permalinks/create', model: [permalink: permalink]
	}

	def save = {
		if(!params.permalink) {
			flash.error = "Permalink Submission not specified"
			return
		}

		def permalink = new SpudPermalink(params.permalink)

		if(permalink.save(flush:true)) {
			redirect(controller: 'permalinks', action: 'index', namespace: 'spud_admin')
		} else {
			flash.error = "Error saving permalink"
			render view: '/spud/admin/permalinks/create', model:[permalink: permalink]
		}
	}

	def edit = {
		def permalink = loadPermalink()

		if(!permalink) {
			return
		}

		render view: '/spud/admin/permalinks/edit', model: [permalink: permalink]
	}

	def update = {
		if(!params.permalink) {
			flash.error = "Permalink Submission not specified"
			redirect controller: 'permalinks', action: 'index', namespace: 'spud_admin'
			return
		}

		def permalink = loadPermalink()
		if(!permalink) {
			return
		}

		params.permalink.each { param ->
			permalink."${param.key}" = param.value
		}

		if(!permalink.save(flush:true)) {
			flash.error = "Error Saving Permalink"
			render view: '/spud/admin/permalinks/edit', model: [permalink: permalink]
			return
		}
		redirect controller: 'permalinks', action: 'index', namespace: 'spud_admin'
	}


	def delete = {
		def permalink = loadPermalink()

		if(!permalink) {
			return
		}
		permalink.delete(flush:true)

		redirect controller: 'permalinks', action: 'index', namespace: 'spud_admin'
	}

	private loadPermalink() {
		if(!params.id) {
			flash.error = "Permalink Submission not specified"
			redirect controller: 'permalink', action: 'index', namespace: 'spud_admin'
			return null
		}

		def permalink = SpudPermalink.get(params.id)
		if(!permalink) {
			flash.error = "Permalink not found!"
			redirect controller: 'permalinks', action: 'index', namespace: 'spud_admin'
			return null
		}
		return permalink
	}


}
