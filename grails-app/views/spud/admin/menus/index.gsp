<g:applyLayout name="spud/admin/detail" >
  <content tag="data_controls">
    <spAdmin:link resource="menus" action="create" class="btn btn-primary" title="New Menu">New Menu</spAdmin:link>
  </content>


	<content tag="detail">
		<div class="page_list">
			<g:each var="menu" in="${menus}">
				<div class="page_row">

					<span class="row_meta"><spAdmin:link controller="menuItems" action="index" params="[menusId: menu.id]">${menu.name}</spAdmin:link>

					<span class="edit_controls">
            <spAdmin:link action="edit" id="${menu.id}" title="Edit ${menu.name}" class="btn btn-sm btn-link">Edit</spAdmin:link>

            <spAdmin:link resource="menus" action="delete" data-method="DELETE" id="${menu.id}" data-confirm="Are you sure you want to remove this menu and all items associated with it?" class="btn btn-sm btn-danger"><span class="glyphicon glyphicon-trash"></span></spAdmin:link>
					</span>
					<br style="clear:both;"/>
				</div>
			</g:each>
      <g:paginate resource="menus" action="index" namespace="spud_admin" total="${menuCount}" max="25" />
	</div>



	</content>
</g:applyLayout>
