package spud.core

class SpudAdminTagLib {
    static defaultEncodeAs = 'html'
    static namespace = 'spAdmin'
    static encodeAsForTags = [logoutLink: 'raw', breadcrumbs: 'raw', pageThumbnail:'raw', link:'raw']

    def grailsApplication
    def spudSecurity

    def currentUserDisplayName = {
    	out << spudSecurity.currentUserDisplayName
    }


    def pageThumbnail = { attrs ->
        def controllerClass = grailsApplication.getArtefactByLogicalPropertyName('Controller', pageScope.controllerName)
        def annotation = controllerClass.clazz.getAnnotation(spud.core.SpudApp)
        if(annotation) {
            attrs = attrs + [src: annotation.thumbnail()]
            out << asset.image(attrs)
        }
    }

    def pageName = { attrs ->
        def controllerClass = grailsApplication.getArtefactByLogicalPropertyName('Controller', pageScope.controllerName)
        def annotation = controllerClass.clazz.getAnnotation(spud.core.SpudApp)
        if(annotation) {
            out << annotation.name()
        }
    }

    def logoutLink = { attrs, body ->
    	attrs = attrs + spudSecurity.logoutUrl
    	out << g.link(attrs,body)
    }

    def breadcrumbs = { attrs ->
        def crumbLinks      = []
        def controllerClass = grailsApplication.getArtefactByLogicalPropertyName('Controller', pageScope.controllerName)
        def annotation      = controllerClass.clazz.getAnnotation(spud.core.SpudApp)
        crumbLinks << link([controller: 'dashboard', action: 'index'],"Dashboard")
        if(annotation) {
            crumbLinks << link([controller: pageScope.controllerName, action: 'index'], "${annotation.name()}")
        }
        if(pageScope.actionName != 'index') {
            crumbLinks << pageScope.actionName //TODO: Title Case this
        }

        out << crumbLinks.join("&nbsp;/&nbsp;")
    }

    def link = { attrs, body ->
        out << g.link(attrs + [namespace: 'spud_admin'],body)
    }
}
