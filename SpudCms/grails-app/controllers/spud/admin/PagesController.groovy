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
    def partials = newPartialsForLayout(page.layout, page.partials)

    render view: '/spud/admin/pages/edit', model: [page: page, layouts: this.layoutsForSite(), partials: partials]
  }

  def update() {
  	def page = loadPage()
    if(!page) {
      return
    }
    page.properties += params.page
    def partialsToDelete = []
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
          if(partial.value.content) {
            partialRecord = new SpudPagePartial(symbolName: partial.key, name: partial.value?.name, content: partial.value.content)
            page.addToPartials(partialRecord)
          }
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

  def delete() {
  	def page = loadPage()
    if(!page) {
      return
    }
    page.delete()
    redirect resource: 'pages', action: 'index', namespace: 'spud_admin'

  }


  def pageParts() {
    def layoutName = params.template ?: grailsApplication.config.spud.cms.defaultLayout ?: 'application'
    def layouts = layoutsForSite()
    def layout = layouts.find { it.layout == layoutName }
    if(!layout) {
      layout = layouts[0]
    }
    if(layout) {
      def page = SpudPage.read(params.id)
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
    return layoutParserService.layoutsForSite(0)
  }

  private newPartialsForLayout(layoutName=null, existingPartials=null) {

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
    if(existingPartials) {
      partials += existingPartials
    }
    if(layout) {
      layout.html.each {
        if(!partials.find{ep -> ep.symbolName == it.parameterize()}) {
          partials << new SpudPagePartial(symbolName: it.parameterize(), name: it, content: null)
        }
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
