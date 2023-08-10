<g:applyLayout name="spud/admin/detail" >

	<content tag="detail">
		<g:form name="new_page" url="[action: 'index',controller: 'pages',namespace: 'spud_admin']" method="POST" class="form-horizontal">
			<g:render template="/spud/admin/pages/form" model="[page: page, layouts: layouts, partials: partials]" />
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-2">
					<g:submitButton name="_submit" value="Create Page" class="btn btn-primary"/>
				</div>	
			</div>
			
		</g:form>
		<asset:javascript src="spud/admin/cms/application.js"/>
		<script type="text/javascript">
			$(document).ready(function() {
				$('input[type=submit].btn-preview').click(function(evt) {

					$(this).button('reset')
					$('#preview').val(1);
					var form = $('form.page_form')[0];
					form.onsubmit = function() {
						window.open('about:blank','preview_window','width=500,height=400')
					};
					form.target = 'preview_window';

				});

				$('input[type=submit].btn-save').click(function(evt) {
					$('#preview').val(0);
						var form = $('form.page_form')[0];
						form.onsubmit = null;
						form.target = null;
				});

			});

		</script>
	</content>
</g:applyLayout>
