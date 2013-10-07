package spud.admin
import  spud.cms.*
import  spud.core.*

@SpudApp(name="Snippets", thumbnail="spud/admin/snippets_thumb.png")
@SpudSecure(['SNIPPETS'])
class SnippetsController {
	static namespace = "spud_admin"

	def index = {

	}
}
