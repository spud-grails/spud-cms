<g:applyLayout name="spud/admin/detail" >

	<content tag="detail">
		<g:form name="edit_page" url="[action: 'update',resource: 'pages',namespace: 'spud_admin', id: page.id, method:'PUT']" method="PUT" class="form-horizontal">
			<g:render template="/spud/admin/pages/form" model="[page: page, layouts: layouts, partials: partials]" />
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-2">
					<g:submitButton name="_submit" value="Save Page" class="btn btn-primary"/>
				</div>	
			</div>
			
		</g:form>
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
