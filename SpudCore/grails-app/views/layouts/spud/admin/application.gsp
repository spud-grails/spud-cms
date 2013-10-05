<!DOCTYPE html>
<html>
<head>
  <title>Spud Admin</title>
  <asset:javascript src="spud/admin/application.js"/>
  <asset:stylesheet href="spud/admin/application.css"/>



  <g:layoutHead/>
</head>
<body>
<div id="header" style="<%=header_style%>">

  <h1>Spud Admin</h1>


	<div id="user_meta">
		<span class="greeting">Hello [Admin]</span>&nbsp;|&nbsp;<g:link controller="spudUser" action="settings">Settings</g:link>&nbsp;|&nbsp;<g:link uri="/logout">Logout</g:link>
	</div>

</div>
<div id="breadcrumbs">
  <spud:breadcrumbs breadCrumbs="${breadCrumbs}"/>

%{-- Add Breadcrumbs Here --}%
</div>
<div id="content">
<% if (flash.notice) {%>
  <div class="alert alert-success">
    <a class="close" data-dismiss="alert">×</a>
    <%= flash.notice %>
  </div>
<% } %>
<% if(flash.warning) {%>
  <div class="alert alert-warning">
    <a class="close" data-dismiss="alert">×</a>
    <%= flash.warning %>
  </div>
<% } %>
<% if(flash.error) {%>

<div class="alert alert-error">
    <a class="close" data-dismiss="alert">×</a>
	  <%= flash.error %>
</div>
<% } %>
  <g:layoutBody/>
</div>




<div id="modal_window" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 class="modal-title"></h3>
  </div>
  <div class="modal-body">

  </div>
  <div class="modal-footer modal-footer-default">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
    <button class="btn btn-primary form-submit">Save changes</button>
  </div>
</div>

</body>
</html>
