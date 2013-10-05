import asset.pipeline.AssetHelper
import asset.pipeline.coffee.CoffeeAssetFile

class CoffeeAssetPipelineGrailsPlugin {
    def version = "0.8.0"
    def grailsVersion = "2.0 > *"
    def title = "CoffeeScript Asset-Pipeline Plugin"
    def author = "David Estes"
    def authorEmail = "destes@bcap.com"
    def description = "Provides coffee-script support for the asset-pipeline static asset management plugin."
    def documentation = "http://github.com/bertramdev/coffee-grails-asset-pipeline"

    def license = "APACHE"
    def organization = [ name: "Bertram Capital", url: "http://www.bertramcapital.com/" ]
    def issueManagement = [ system: "GITHUB", url: "http://github.com/bertramdev/coffee-grails-asset-pipeline/issues" ]
    def scm = [ url: "http://github.com/bertramdev/coffee-grails-asset-pipeline" ]

    def doWithDynamicMethods = { ctx ->
        AssetHelper.assetSpecs << CoffeeAssetFile
    }
}
