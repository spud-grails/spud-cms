<g:applyLayout name="spud/admin/application" >

<html>
	<head>
		<title><g:layoutTitle/></title>
		<g:layoutHead/>
  </head>
  <body>
  	<div id="detail_wrapper">
  	<span class="data_controls">
        <g:pageProperty name="page.data_controls"/>
  		</span>
  		<h2>
  			<%if(pageThumbnail) {%>
  				<span class="thumb_wrapper"><img src="/assets/${pageThumbnail}"/></span>
  			<%}%>
  			${pageName}
  		</h2>

  		<div id="details">
  			<g:pageProperty name="page.detail"/>
  		</div>
  	</div>
  </body>
</html>
</g:applyLayout>
