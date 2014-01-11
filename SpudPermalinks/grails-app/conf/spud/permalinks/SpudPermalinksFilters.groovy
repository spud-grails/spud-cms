package spud.permalinks

class SpudPermalinksFilters {
    def grailsapplication

    def filters = {
        all(uri: '/**') {
            before = {
                if(controllerName == 'assets') {
                    return
                }
                // Prefer whats in web-app/assets instead of the other
                def permalinkUri = request.forwardURI
                if(request.contextPath && request.contextPath != "/") {
                    permalinkUri = permalinkUri.substring(request.contextPath.size())
                }
                if(permalinkUri.size() == 0) {
                    return true
                }
                // TODO: Determine SiteId
                def siteId = 0

                def permalinks = SpudPermalink.withCriteria(readOnly:true) {
                    eq('siteId', siteId)
                    or {
                        eq('urlName', permalinkUri)
                        if(permalinkUri.startsWith("/") && permalinkUri.size() > 1) {
                            eq('urlName', permalinkUri.substring(1))
                        }
                    }
                }
                if(permalinks) {
                    def permalink = permalinks[0]
                    if(!permalink.destinationUrl.startsWith("/")) {
                        if(permalink.destinationUrl ==~ /(http|https|ftp)\:\/\/.*/) {
                            redirect url: permalinks[0].destinationUrl, permanent: true
                        } else {
                            redirect uri: "/" + permalinks[0].destinationUrl, permanent: true
                        }

                    } else {
                        redirect uri: permalinks[0].destinationUrl, permanent: true
                    }
                    return false;
                }
            }
        }
    }
}
