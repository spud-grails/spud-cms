package asset.pipeline.coffee
import asset.pipeline.CacheManager
import asset.pipeline.AssetHelper

class CoffeeAssetFile {
	static final String contentType = 'application/javascript'
	static extensions = ['coffee', 'js.coffee']
	static final String compiledExtension = 'js'
	static processors = [CoffeeScriptProcessor]

	File file
	def baseFile

	CoffeeAssetFile(file, baseFile=null) {
		this.file = file
		this.baseFile = baseFile
	}

	def processedStream(precompiler=false) {
		def fileText = file?.text
		def md5 = AssetHelper.getByteDigest(fileText.bytes)
		if(!precompiler) {
			def cache = CacheManager.findCache(file.canonicalPath, md5)
			if(cache) {
				return cache
			}
		}
		for(processor in processors) {
			def processInstance = processor.newInstance(precompiler)
			fileText = processInstance.process(fileText, this)
		}
		if(!precompiler) {
			CacheManager.createCache(file.canonicalPath,md5,fileText)
		}
		return fileText
		// Return File Stream
	}

	def directiveForLine(line) {
		line.find(/#=(.*)/) { fullMatch, directive -> return directive }
	}
}
