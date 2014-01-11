<g:applyLayout name="spud/admin/detail" >

<content tag="data_controls">
  <spAdmin:link action="create" resource="menus/menuItems" menusId="${menu.id}" title="New Menu Item" class="btn btn-primary">New Menu Item</spAdmin:link>
</content>
<content tag="detail">
	<p class="lead">Drag and drop to rearrange your menus.</p>
	<ul id="root_menu_list" class="menu_list page_list sortable connectedSortable">
    <g:each var="menuItem" in="${menuItems}">
      <li>
	      <g:render template="/spud/admin/menu_items/menu_item_row" model="[menuItem: menuItem, menu: menu]"/>
      </li>
		</g:each>
	</ul>
  <script type="text/javascript">
		$(document).ready(function() { spud.admin.cms.menu_items.init(${menu.id},"${createLink(controller: 'menuItems', namespace: 'spud_admin', method: 'PUT', action: 'sort', params:[menusId: menu.id])}");})
	</script>
</content>

</g:applyLayout>


