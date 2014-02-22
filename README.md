Spud CMS
========

Spud CMS is a CMS Engine designed to be robust, easy to use, and light weight.


Installation/Usage
------------------

Add spud-cms to your BuildConfig.groovy

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


Using Liquid Template Engine
----------------------------
Spud CMS utilizes the liquid template syntax engine created by Shopify. This allows you to easily inject variables into your pages in the page editor. Example:

    **TODO** Template syntax is still unavailable in the grails version, coming soon!

Using Menus
-----------
A lot of cms engines allow you to render your navigation links in a ul block by using your page tree structure. In a lot of cases this is insufficient as some sites have urls that redirect to pages outside of your cms. This is where menus come in. They can be built in the spud admin control panel.
In your application layout file or any erb template you can render a ul block like so

    <g:menu name="Main" id="navigation"/>

This will output a <ul id="navigation"></ul> block for the menu you created in admin named "Main"


