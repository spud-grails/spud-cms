package asset.pipeline.coffee

import org.mozilla.javascript.Context
import org.mozilla.javascript.Scriptable
import org.springframework.core.io.ClassPathResource

// CoffeeScript engine uses Mozilla Rhino to compile the CoffeeScript template
// using existing javascript in-browser compiler
class CoffeeScriptProcessor {

  Scriptable globalScope
  ClassLoader classLoader
  def precompilerMode

  CoffeeScriptProcessor(precompiler=false){
    try {
      this.precompilerMode = precompiler
      classLoader = getClass().getClassLoader()

      def coffeeScriptJsResource = new ClassPathResource('asset/pipeline/coffee/coffee-script-1.6.1.js', classLoader)
      assert coffeeScriptJsResource.exists() : "CoffeeScriptJs resource not found"

      def coffeeScriptJsStream = coffeeScriptJsResource.inputStream

      Context cx = Context.enter()
      cx.setOptimizationLevel(-1)
      globalScope = cx.initStandardObjects()
      cx.evaluateReader(globalScope, new InputStreamReader(coffeeScriptJsStream, 'UTF-8'), coffeeScriptJsResource.filename, 0, null)
    } catch (Exception e) {
      throw new Exception("CoffeeScript Engine initialization failed.", e)
    } finally {
      try {
        Context.exit()
      } catch (IllegalStateException e) {}
    }
  }

  def process(input, assetFile) {
    try {
      def cx = Context.enter()
      def compileScope = cx.newObject(globalScope)
      compileScope.setParentScope(globalScope)
      compileScope.put("coffeeScriptSrc", compileScope, input)
      def result = cx.evaluateString(compileScope, "CoffeeScript.compile(coffeeScriptSrc)", "CoffeeScript compile command", 0, null)
      return result
    } catch (Exception e) {
      throw new Exception("""
        CoffeeScript Engine compilation of coffeescript to javascript failed.
        $e
        """)
    } finally {
      Context.exit()
    }
  }
}
