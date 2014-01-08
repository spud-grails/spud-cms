Spud Core Admin
===============

Spud Admin is a dependency package that adds a nice looking administrative panel to any project you add it to. It supports easy grails app integration and there are several planned future engines that we plan on designing for the spud suite. The first of which is Spud CMS which is being worked on now.

Installation/Usage
------------------

TODO: Add installation information here


Adding Your Own Engines
-----------------------

Creating a grails plugin that ties into spud admin is fairly straight forward. Using the power of annotations, controllers can be registered as an administrative module:

```groovy
package spud.admin
import  spud.core.*
import  spud.security.*

@SpudApp(name="Users", thumbnail="spud/admin/users_thumb.png")
@SpudSecure(['USERS'])
class UserController {
	static namespace = 'spud_admin'


	def index = {
		def users = SpudUser.list([max:25] + params)
		render view: '/spud/admin/users/index', model:[users: users, userCount: SpudUser.count()]
	}
}
```

The example above uses the `@SpudApp` annotation to define the controller as a Users admin application. This will be displayed on the administrative dashboard for user management.

You can use the layouts provided with spud admin by using 'spud/admin/application' or 'spud/admin/detail' layouts

When creating controllers for the admin panel create them in the spud.admin classpath and preferably use `resource` REST style UrlMappings

Testing
-----------------

TODO: Add Testing Information

NOTE: Spud Core is Retina Resolution Compatible Now

License
-------
This
