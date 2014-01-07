package spud.permalinks

class SpudPermalinksFilters {

    def filters = {
        all(uri: '/**') {
            before = {
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

                def permalinks = SpudPermalink.createCriteria().list {
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
                        redirect uri: "/" + permalinks[0].destinationUrl, permanent: true
                    } else {
                        redirect uri: permalinks[0].destinationUrl, permanent: true
                    }
                    return false;
                }
            }
        }
    }
}
