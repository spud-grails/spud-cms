Spud CMS
========

Spud CMS is a CMS Engine designed to be robust, easy to use, and light weight.


Installation/Usage
------------------

Add spud-cms to your BuildConfig.groovy
```groovy
 plugins {
    compile ':spud-cms:0.3.1'
    compile ':spud-security:0.2.1' //Only necessary if you do not have a security bridge
 }
```

**Security**: By Default Spud CMS Expects to interact with The [Grails Security Bridge](http://grails.org/plugin/security-bridge) interface. If you do not have an existing security implementation than be sure to include the latest spud-security.

**NOTE**: Spud Core is a dependency and looks for hibernate version equivalent to Grails 2.3.8. If necessary add spud-core to your plugins list and exclude hibernate.

Routing to the CMS Engine
--------------------------
Routing your home page to the CMS engine is fairly straight forward.
in your applications Config.groovy file add a configure block as such

```groovy
grails {
  spud {
    cms {
      rootPageName = "home"
      defaultLayout = "page"
    }
  }
}
```


Where "home" is the page name you wish to use.

Pages will default render to the 'page' layout of your application. You can change this by using templates to specify base layouts.

Defining Templates / Layouts
----------------------------
In Spud CMS templates are defined similar to how an asset pipeline manifest is defined. At the top of your layout files a series of comments can be used to define the name of the template, and the content blocks that are editable by the user.

Example:

    <%
    //= template_name 2 Column Page
    //= html Left
    //= html Right
    %>

Optionally a description can be passed as well as a `site_name default` for multisite mode.

Within your layout you will want to render these content blocks via a pageProperty taglib (sitemesh)

```
<g:pageProperty name="page.left"/>
<g:pageProperty name="page.right"/>
```

For more information checkout the Wiki section of this repository.


Using Handlebars Template Engine
----------------------------
Spud CMS utilizes the [Handlebars.java](http://jknack.github.io/handlebars.java/) template syntax engine in any content editable area. This maps any calls to handlebars helpers to the grails `sp` taglib namespace. Simply add your own taglib in the sp namespace to get access.

```handlebars
<h1>My Awesome Page</h1>
<p>This is cool , lets render a snippet.</p>
{{{snippet name='Test Snippet'}}}

<div id='footer'>
{{{snippet name='Footer'}}}
</div>
```

Using Menus
-----------
A lot of cms engines allow you to render your navigation links in a ul block by using your page tree structure. In a lot of cases this is insufficient as some sites have urls that redirect to pages outside of your cms. This is where menus come in. They can be built in the spud admin control panel.
In your application layout file or any erb template you can render a ul block like so

    <sp:menu name="Main" id="navigation"/>

This will output a <ul id="navigation"></ul> block for the menu you created in admin named "Main"

Using an Alternative DataSource
-------------------------------

Spud supports running on a different datasource than your primary. This can be done by adding the following config example:

```groovy
spud {
	core {
		//By default this uses the DEFAULT datasource
		datasource = 'spud' //Set datasource name here
	}
}
```
