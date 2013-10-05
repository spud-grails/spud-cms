// includeTargets << new File(assetPipelinePluginDir, "scripts/_AssetCompile.groovy")

eventAssetPrecompileStart = { assetConfig ->
	assetConfig.specs << 'asset.pipeline.coffee.CoffeeAssetFile'
}
