<g:applyLayout name="spud/admin/detail" >
	<content tag="detail">
		<g:form name="edit_menu_item" url="[action: 'update', method:'PUT', resource: 'menus/menuItems',menusId: menu.id,namespace: 'spud_admin', id: menuItem.id]" method="PUT" class="form-horizontal">
			<g:render template="/spud/admin/menu_items/form" model="[menuItem: menuItem, menu: menu]" />
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-2">
					<g:submitButton name="_submit" value="Save Menu Item" class="btn btn-primary"/> or <spAdmin:link action="index" resource="menus/menuItems" menusId="${menu.id}" class="btn btn-default">cancel</spAdmin:link>	
				</div>
				
		  </div>
		</g:form>
	</content>
</g:applyLayout>
