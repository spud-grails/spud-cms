<g:applyLayout name="spud/admin/detail" >

	<content tag="detail">
		<g:form name="edit_menu" url="[action: 'update', method:'PUT', resource: 'menus', id: menu.id ,namespace: 'spud_admin']" method="PUT" class="form-horizontal">
			<g:render template="/spud/admin/menus/form" model="[menu: menu]" />
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-2">
					<g:submitButton name="_submit" value="Save Menu" class="btn btn-primary"/> or <spAdmin:link action="index" resource="menus" class="btn btn-default">cancel</spAdmin:link>	
				</div>
				
		  </div>
		</g:form>
	</content>
</g:applyLayout>
