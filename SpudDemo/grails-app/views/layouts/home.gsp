<%
//= template_name Home
//= html Body
%>
<sp:applyLayout name="app">
	<head>
		<g:layoutHead/>
	</head>
	<content tag="appContent">
		<div class="container">
			<div class="row">
				<h1>Welcome to Spud Demo!</h1>
			</div>
		</div>

		<div class="container">
			<div class="row">
				<div class="col-sm-12">
					<g:pageProperty name="page.body"/>
				</div>

			</div>
		</div>
	</content>
</sp:applyLayout>
