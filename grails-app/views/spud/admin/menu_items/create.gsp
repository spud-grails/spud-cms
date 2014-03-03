<g:applyLayout name="spud/admin/detail" >
	<content tag="detail">
		<g:form name="new_menu_item" url="[action: 'save', method:'POST', resource: 'menus/menuItems',menusId: menu.id,namespace: 'spud_admin']" method="POST" class="form-horizontal">
			<g:render template="/spud/admin/menu_items/form" model="[menuItem: menuItem, menu: menu]" />
			<div class="form-group">
				<div class="col-sm-8 col-sm-offset-2">
					<g:submitButton name="_submit" value="Create Menu Item" class="btn btn-primary"/> or <spAdmin:link action="index" resource="menus/menuItems" menusId="${menu.id}" class="btn btn-default">cancel</spAdmin:link>
				</div>
		  </div>
		</g:form>
	</content>
</g:applyLayout>
