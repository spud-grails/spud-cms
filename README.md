Spud CMS
========

Spud CMS is a CMS Engine designed to be robust, easy to use, and light weight.


Installation/Usage
------------------

Add spud-cms to your BuildConfig.groovy
```groovy
 plugins {
    compile ':spud-cms:0.6.3'
    compile ':spud-security:0.6.0' //Only necessary if you do not have a security bridge
 }
```

**Security**: By Default Spud CMS Expects to interact with The [Grails Security Bridge](http://grails.org/plugin/security-bridge) interface. If you do not have an existing security implementation than be sure to include the latest spud-security.

**NOTE**: Hibernate is required, (version set 3 or 4 from grails 2.3.x or 2.4.x)


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
      cacheEnabled = false //Turn on action caching
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


MultiSite Mode
--------------

Spud CMS Now Supports hosting multiple websites in one running instance!. This is great if you have multiple websites to manage and dont want to run a lot of high memory instances of spud grails.
It's also great if you have marketing campaigns. By default, Spud utilizes the `SpudDefaultMultiSiteService` which implements the `SpudMultiSiteProvider` interface. This means all your multisite configuration is done via the `Config.groovy` file.


```groovy
spud {
  core {
    defaultSiteId = 0
    sites = [
      [siteId: 1, shortName: 'test', name: 'My Subsite', hosts: ['test.spudengine.local']]
    ]
  }
}

```

What this does is add a second site with an identifier of `1` and a template shortname of `test` to your project. The hosts array will control wether or not this site id is rendered.
In this example, if a user browsers your grails site from `test.spudengine.local` then the context is automatically switched to siteId 1.

You can control your layout/template availability by using the site directive on templates:

```
<%
//= template_name 2 Column Page
//= html Left
//= html Right
//= site test default
%>
```
Above this theme is designated as available for the site with a `shortName` of 'test' and 'default'. Default enables this for the default site bahavior. If a site directive is not specified in the template, it is assumed that the template is available for All sites.

Managing content for each individual site is as simple as visiting the administrative dashboard `spud/admin`. In the upper right is a dropdown box which controls the active site context. Change this dropdown to designate which site you would like to work on.
