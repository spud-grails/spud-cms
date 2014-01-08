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

				<span class="thumb_wrapper">
          <spAdmin:pageThumbnail/>
        </span>

  			<spAdmin:pageName/>
  		</h2>

  		<div id="details">
  			<g:pageProperty name="page.detail"/>
  		</div>
  	</div>
  </body>
</html>
</g:applyLayout>
