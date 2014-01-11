<%
//= template_name Page
//= html Body
//= html Sidebar
%>
<sp:applyLayout name="app">
	<content tag="appContent">
		<div class="container">
			<div class="row">
				<h1>${page?.name ?: 'Page Layout'}</h1>
			</div>
		</div>

		<div class="container">
			<div class="row">
				<div class="col-sm-9">
					<g:pageProperty name="page.body"/>
				</div>
				<div class="col-sm-3">
					<g:pageProperty name="page.sidebar"/>
				</div>
			</div>
		</div>
	</content>
</sp:applyLayout>
