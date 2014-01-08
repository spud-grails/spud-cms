class SpudSecurityGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Spud Security Plugin"
    def author = "David Estes"
    def authorEmail = "destes@bcap.com"
    def description = "Implements Security, using Spring Security Core, for SpudCore and the rest of the spud suite. Spud Security also provides user models and role models that can be managed from a convenient administrative panel within the spud admin."

    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/spud-security"
    def license = "APACHE"
    def organization = [name: "Bertram Labs", url: "http://www.bertramlabs.com/"]
    def issueManagement = [system: "GITHUB", url: "https://github.com/bertramdev/spud-grails/issues"]
    def scm = [url: "https://github.com/bertramdev/spud-grails"]


    def doWithSpring = {
        application.config.spud.securityService = 'spudSecurityService'
        application.config.grails.plugins.springsecurity.password.algorithm = 'SHA-512'
        application.config.grails.plugins.springsecurity.userLookup.userDomainClassName = 'spud.security.SpudUser'
        application.config.grails.plugins.springsecurity.userLookup.usernamePropertyName = 'login'
        application.config.grails.plugins.springsecurity.password.algorithm = 'SHA-512'
        application.config.grails.plugins.springsecurity.authority.className = 'spud.security.SpudRole'
        application.config.grails.plugins.springsecurity.authority.nameField = 'authority'
        application.config.grails.plugins.springsecurity.userLookup.authoritiesPropertyName = 'authorities'

        springConfig.addAlias "spudSecurity", 'spudSecurityService'
    }
}
