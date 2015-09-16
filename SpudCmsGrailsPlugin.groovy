class SpudCmsGrailsPlugin {
    def version = "0.6.18"
    def grailsVersion = "2.3 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Spud Cms Plugin" // Headline display name of the plugin
    def author = "David Estes"
    def authorEmail = "destes@bcap.com"
    def description = 'Provides CMS functionality for Spud'

    def documentation = "https://github.com/spud-grails/spud-cms"
    def license = "APACHE"
    def organization = [name: "Bertram Labs", url: "http://www.bertramlabs.com/"]
    def issueManagement = [system: "GITHUB", url: "https://github.com/spud-grails/spud-cms/issues"]

}
