package spud.core

class SpudCoreTagLib {
    static defaultEncodeAs = 'html'
    static namespace = 'spud'
    static encodeAsForTags = [logoutLink: 'raw', breadcrumbs: 'raw']

    def grailsApplication
    def spudSecurity

    def currentUserDisplayName = {
    	out << spudSecurity.currentUserDisplayName
    }

    def logoutLink = { attrs, body ->
    	attrs = attrs + spudSecurity.logoutUrl
    	out << g.link(attrs,body)
    }

    def breadcrumbs = {

    }
}
