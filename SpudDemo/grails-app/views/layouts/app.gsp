<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <g:if test="${page}">
    <title>${page.name} | Spud Demo</title>
    </g:if>
    <g:else>
      <title><g:layoutTitle default="Spud Demo"/></title>
    </g:else>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <asset:stylesheet src="application.css"/>

    <g:layoutHead/>

  </head>
  <body>
    <nav class="navbar navbar-fixed-top navbar-default" role="navigation">
      <div class="container">
      <!-- Brand and toggle get grouped for better mobile display -->
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
        <g:link controller="page" action="show" format="html" class="navbar-brand">Spud Demo</g:link>

      </div>

      <!-- Collect the nav links, forms, and other content for toggling -->
      <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
        <sp:menu name="Main" class="nav navbar-nav" activeClass="active"/>
        %{-- <ul class="nav navbar-nav">
          <li><g:link controller="page" action="show" format="html" id="about-us">About Us</g:link></li>

        </ul> --}%

      </div><!-- /.navbar-collapse -->
      </div>
    </nav>

    <g:pageProperty name="page.appContent"/>



    <div class="footer" role="contentinfo">
        <div class="container">
          <sp:snippet name="Footer"/>
        </div>
    </div>

    <asset:javascript src="application.js"/>
  </body>
</html>

