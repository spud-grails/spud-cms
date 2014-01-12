<g:applyLayout name="spud/admin/detail" >
	<content tag="detail">
		<g:form name="new_menu_item" url="[action: 'save', method:'POST', resource: 'menus/menuItems',menusId: menu.id,namespace: 'spud_admin']" method="POST" class="form-horizontal">
			<g:render template="/spud/admin/menu_items/form" model="[menuItem: menuItem, menu: menu, menuParentOptions: menuParentOptions]" />
			<div class="form-actions">
				<g:submitButton name="_submit" value="Create Menu Item" class="btn btn-primary"/> or <spAdmin:link action="index" resource="menus/menuItems" menusId="${menu.id}" class="btn">cancel</spAdmin:link>
		  </div>
		</g:form>
	</content>
</g:applyLayout>
