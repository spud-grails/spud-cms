package spud.admin
import  spud.cms.*
import  spud.core.*
import grails.transaction.Transactional
import grails.artefact.Artefact

@SpudApp(name="Pages", thumbnail="spud/admin/pages_thumb.png")
@SpudSecure(['PAGES'])
@Artefact("Controller")
class PagesController {
	static namespace = 'spud_admin'
	def grailsApplication
  def layoutParserService

  def index() {
  	def pages = SpudPage.list([sort: 'pageOrder', spudPage: null] + params)
		render view: '/spud/admin/pages/index', model:[pages: pages, pageCount: SpudPage.count()]
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
    println "Printing Partials Hash"
    println params.partial
    params.partial.each { partial ->
      if(partial.key.indexOf(".") == -1) {
        def partialRecord = new SpudPagePartial(symbolName: partial.key, name: partial.value?.name, content: partial.value.content)
        page.addToPartials(partialRecord)
      }
    }

    if(page.save(flush:true)) {
      redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
    } else {
      flash.error = "Error Saving Page"
      def partials = page.partials
      render view: '/spud/admin/pages/create', model:[page: page, layouts: this.layoutsForSite(), partials: partials]
    }

  }



  def edit() {
  	def page = loadPage()
    if(!page) {
      return
    }
    render view: '/spud/admin/pages/edit', model: [page: page, layouts: this.layoutsForSite(), partials: page.partials]
  }

  def update() {
    println "Updating Page"
  	def page = loadPage()
    if(!page) {
      return
    }
    page.properties += params.page

    params.partial.each { partial ->
      if(partial.key.indexOf(".") == -1) {
        def partialRecord = page.partials.find { it.symbolName == partial.key}
        if(!partialRecord) {
          partialRecord = new SpudPagePartial(symbolName: partial.key, name: partial.value?.name, content: partial.value.content)
          page.addToPartials(partialRecord)
        } else {
          partialRecord.content = partial.value.content
          partialRecord.save(flush:true)
        }
      }
    }


    if(page.save(flush:true)) {
      redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
    } else {
      render view: '/spud/admin/pages/edit', model: [page: page, layouts: this.layoutsForSite(), partials: page.partials]
    }


  }

  def delete = {
  	def page = loadPage()
    if(!page) {
      return
    }
    page.delete()
    redirect resource: 'pages', action: 'index', namespace: 'spud_admin'

  }

  private layoutsForSite() {
    return layoutParserService.layoutsForSite(0)
  }

  private newPartialsForLayout(layoutName=null) {

    def layoutsForSite  = layoutParserService.layoutsForSite(0)
    def defaultLayoutName = grailsApplication.config.spud.cms.defaultLayout ?: 'application'
    if(!layoutName) {
      layoutName = defaultLayoutName
    }

    def layout = layoutsForSite.find { it.layout == layoutName}
    if(!layout) {
      layout = layoutsForSite[0]
    }

    def partials = []
    if(layout) {
      layout.html.each {
        partials << new SpudPagePartial(symbolName: it.parameterize(), name: it, content: null)
      }
    }
    return partials
  }

  private loadPage() {
  	if(!params.id) {
			flash.error = "Page Submission not specified"
			redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
			return null
		}

		def page = SpudPage.read(params.id)
		if(!page) {
			flash.error = "Page not found!"
			redirect resource: 'pages', action: 'index', namespace: 'spud_admin'
			return null
		}
		return page
  }


}
