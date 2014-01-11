class SpudCmsGrailsPlugin {
    def groupId = "com.bertramlabs"
    def version = "0.1"
    def grailsVersion = "2.3 > *"
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Spud Cms Plugin" // Headline display name of the plugin
    def author = "David Estes"
    def authorEmail = "destes@bcap.com"
    def description = '''\
Provides CMS functionality for spud
'''

    def documentation = "http://grails.org/plugin/spud-cms"
    def license = "APACHE"
    def organization = [name: "Bertram Labs", url: "http://www.bertramlabs.com/"]
    def issueManagement = [system: "GITHUB", url: "https://github.com/bertramdev/spud-grails/issues"]
    def scm = [url: "https://github.com/bertramdev/spud-grails"]
}
