grails.project.work.dir = 'target'
grails.project.target.level = 1.6
grails.project.source.level = 1.6

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    inherits 'global'
    log "warn"
    repositories {
        grailsCentral()
        grailsPlugins()
        mavenCentral()
    }

    plugins {
        if(System.getProperty('plugin.mode') != 'local') {
            runtime ":spud-core:0.6.1"
            runtime ":spud-permalinks:0.6.0"

            runtime(':hibernate4:4.3.5.4') {
                export = false
            }
        }
        runtime ':cache:1.1.7'
        runtime ':sitemaps:1.0.0'


        build(":release:3.0.1",
              ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}

if(System.getProperty('plugin.mode') == 'local') {
    grails.plugin.location."spud-core" = "../spud-core"
    grails.plugin.location."spud-permalinks" = "../spud-permalinks"
}
