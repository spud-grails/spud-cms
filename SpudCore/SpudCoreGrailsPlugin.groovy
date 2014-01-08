class SpudCoreGrailsPlugin {

    def groupId = "com.bertramlabs"
    def version = "0.1-SNAPSHOT"
    def grailsVersion = "2.3 > *"
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]

    def title = "Spud Core Plugin" // Headline display name of the plugin
    def author = "David Estes"
    def authorEmail = "destes@bcap.com"
    def description = '''\
	    Spud Admin is a dependency package that adds a nice looking administrative panel to any project you add it to. It supports easy grails app integration and provides core functionality for spud modules.
		'''

    def documentation = "http://grails.org/plugin/spud-core"
    def license = "APACHE"
    def organization = [name: "Bertram Labs", url: "http://www.bertramlabs.com/"]
    def issueManagement = [system: "GITHUB", url: "https://github.com/bertramdev/spud-grails/issues"]
    def scm = [url: "https://github.com/bertramdev/spud-grails"]

    def doWithSpring = {
        def beanName = application.config.spud.securityService ? application.config.spud.securityService : 'abstractSpudSecurityService'
        springConfig.addAlias "spudSecurity", beanName

        application.config.spud.renderers = application.config.spud.renderers ?: [:]
        application.config.spud.templateEngines = application.config.spud.templateEngines ?: [:]
        application.config.spud.renderers.gsp = 'defaultSpudRendererService'
        application.config.spud.templateEngines.system = 'defaultSpudTemplateService'
    }

    def doWithApplicationContext = { ctx ->
        ctx.adminApplicationService.initialize()

    }
}
