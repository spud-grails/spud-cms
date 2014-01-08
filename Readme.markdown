Spud Site Management Engine
===============================

The Spud Grails site management engine is a suite of plugins used for managing content dynamically on your grails site. They are drop in plugins that can be extended to run in almost any existing grails 2.3.x application. This is a port of the already production ready spud-rails gem for Rails platforms.

NOTE: This plugin will be moving to individual repositories soon but for now this is where it resides and it is in very early development.

Getting Started
------------------

The following plugins are currently available for being expanded on:

*SpudCms - CMS Page Management Engine
*SpudSecurity - Security Module for integrating security and user management to spud
*SpudPermalinks - Permalink Management, used for handling url mapping redirects from legacy sites to new site urls

To get up and running it may be best to run SpudDemo as a grails 2.3.4 application. You can see development progression by pointing your browser to:

```
http://localhost:8080/SpudDemo/spud/admin
```


Contributing
------------

All contributions are of course welcome, as we have a long way to go.

* Spock Testing Framework Integration
* Documentation
* UserGroup / Role management and restriction integration for SpudSecurity
* Snippet Management and taglibs (SpudCMS)
* Menu Management and taglibs (SpudCMS)
* Configuration Options
* Scaffold Generators for generating admin modules


License
-------
APACHE
