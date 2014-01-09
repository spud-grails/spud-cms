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


    def doWithDynamicMethods = {
        String.metaClass.camelize = {
          delegate.split("-").inject(""){ before, word ->
            before += word[0].toUpperCase() + word[1..-1]
          }
        }

        String.metaClass.underscore = {
            def output = delegate.replaceAll("-","_")
            output.replaceAll(/\B[A-Z]/) { '_' + it }.toLowerCase()
        }

        String.metaClass.humanize = {
            def output = delegate.replaceAll(/[\_\-]+/," ")
        }

        String.metaClass.parameterize = {
            def output = delegate.replaceAll(/[^A-Za-z0-9\-_]+/,"-").toLowerCase()
        }

        String.metaClass.titlecase = {
            def output = delegate.replaceAll( /\b[a-z]/, { it.toUpperCase() })
        }

    }

    def doWithSpring = {
        def beanName = application.config.spud.securityService ? application.config.spud.securityService : 'abstractSpudSecurityService'
        springConfig.addAlias "spudSecurity", beanName

        application.config.spud.renderers = application.config.spud.renderers ?: [:]
        application.config.spud.layoutEngines = application.config.spud.layoutEngines ?: [:]
        application.config.spud.renderers.gsp = 'defaultSpudRendererService'
        application.config.spud.layoutEngines.system = 'defaultSpudLayoutService'

        // Load In Cached Layout List
        if(application.warDeployed) {
            application.config.spud.core.layouts = []
            def layoutList = application.parentContext.getResource("WEB-INF/spudLayouts.txt")
            if(layoutList.exists()) {
                def contents = layoutList.inputStream.text
                if(contents) {
                    application.config.spud.core.layouts = contents.split("\n")
                }
            }
            println "Spud Layouts Loaded ${application.config.spud.core.layouts}"
        }

    }

    def doWithApplicationContext = { ctx ->
        ctx.adminApplicationService.initialize()

    }
}
