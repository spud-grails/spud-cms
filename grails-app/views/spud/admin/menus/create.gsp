<g:applyLayout name="spud/admin/detail" >

	<content tag="detail">
		<g:form name="new_menu" url="[action: 'save', method:'POST', resource: 'menus',namespace: 'spud_admin']" method="POST" class="form-horizontal">
			<g:render template="/spud/admin/menus/form" model="[menu: menu]" />
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-2">
					<g:submitButton name="_submit" value="Create Menu" class="btn btn-primary"/> or <spAdmin:link action="index" resource="menus" class="btn btn-default">cancel</spAdmin:link>
				</div>	
			</div>
		</g:form>
	</content>
</g:applyLayout>
