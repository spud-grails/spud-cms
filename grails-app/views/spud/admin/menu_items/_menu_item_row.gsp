<div class="page_row menu-item-row" data-menu-item-id="${menuItem.id}">

	<span class="row_meta">
		<spAdmin:link action="edit" resource="menus/menuItems" menusId="${menu.id}" id="${menuItem.id}">${menuItem.name}</spAdmin:link>
	</span>

	<span class="edit_controls">
		<spAdmin:link action="edit" resource="menus/menuItems" menusId="${menu.id}" id="${menuItem.id}" title="Edit Menu Item" class="btn btn-sm btn-link">Edit</spAdmin:link>
		<spAdmin:link action="delete" resource="menus/menuItems" menusId="${menu.id}" method="DELETE" data-method="DELETE" data-confirm="Are you sure  you want to remove this menu item?" id="${menuItem.id}" title="Delete Menu Item" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-trash"></span></spAdmin:link>
	</span>
	<br style="clear:both;"/>
</div>
<ul class="menu_list subitem sortable connectedSortable">
	<g:each var="menuSubItem" in="${menuItem.menuItems}">
		<li>
      <g:render template="/spud/admin/menu_items/menu_item_row" model="[menuItem: menuSubItem, menu: menu]"/>
		</li>
	</g:each>
</ul>
