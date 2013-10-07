package spud.admin
import  spud.cms.*
import  spud.core.*

@SpudApp(name="Pages", thumbnail="spud/admin/pages_thumb.png")
@SpudSecure(['PAGES'])
class PagesController {
	static namespace = 'spud_admin'
	def spudTemplateService
	def grailsApplication

  def index = {
  	def pages = SpudPage.list([sort: 'pageOrder', spudPage: null] + params)
		render view: '/spud/admin/pages/index', model:[pages: pages, pageCount: SpudPage.count()]
  }

  def create = {
  	def page = new SpudPage()
  	def layoutsForSite = spudTemplateService.layoutsForSite(0)
  	def defaultLayoutName = grailsApplication.config.spud.cms.defaultLayout ?: 'application'
  	def defaultLayout = layoutsForSite.find { it.name == defaultLayoutName }
  	def partials = []
  	if(defaultLayout) {
  		defaultLayout.partials.each {
  			partials << new SpudPagePartial(name: it.name, content: null)
  		}
  	}
  	println partials
  	render view: '/spud/admin/pages/create', model:[page: page, layouts: layoutsForSite, partials: partials]
  }

  def save = {

  }

  def edit = {
  	def page = loadPage()
  }

  def update = {
  	def page = loadPage()
  }

  def delete = {
  	def page = loadPage()
  }


  private loadPage() {
  	if(!params.id) {
			flash.error = "Page Submission not specified"
			redirect controller: 'pages', action: 'index', namespace: 'spud_admin'
			return null
		}

		def page = SpudPage.get(params.id)
		if(!page) {
			flash.error = "Page not found!"
			redirect controller: 'pages', action: 'index', namespace: 'spud_admin'
			return null
		}
		return page
  }
}
