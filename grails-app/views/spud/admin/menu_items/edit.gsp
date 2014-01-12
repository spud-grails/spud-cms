<g:applyLayout name="spud/admin/detail" >
	<content tag="detail">
		<g:form name="edit_menu_item" url="[action: 'update', method:'PUT', resource: 'menus/menuItems',menusId: menu.id,namespace: 'spud_admin', id: menuItem.id]" method="PUT" class="form-horizontal">
			<g:render template="/spud/admin/menu_items/form" model="[menuItem: menuItem, menu: menu, menuParentOptions: menuParentOptions]" />
			<div class="form-actions">
				<g:submitButton name="_submit" value="Save Menu Item" class="btn btn-primary"/> or <spAdmin:link action="index" resource="menus/menuItems" menusId="${menu.id}" class="btn">cancel</spAdmin:link>
		  </div>
		</g:form>
	</content>
</g:applyLayout>
