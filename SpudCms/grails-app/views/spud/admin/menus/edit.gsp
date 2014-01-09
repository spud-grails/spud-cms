<g:applyLayout name="spud/admin/detail" >

	<content tag="detail">
		<g:form name="edit_menu" url="[action: 'update', method:'PUT', resource: 'menus', id: menu.id ,namespace: 'spud_admin']" method="PUT" class="form-horizontal">
			<g:render template="/spud/admin/menus/form" model="[menu: menu]" />
			<div class="form-actions">
				<g:submitButton name="_submit" value="Save Menu" class="btn btn-primary"/> or <spAdmin:link action="index" resource="menus" class="btn">cancel</spAdmin:link>
		  </div>
		</g:form>
	</content>
</g:applyLayout>
