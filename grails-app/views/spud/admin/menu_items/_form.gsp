<fieldset>
<div class="form-group">
	<label for="menuItem.name" class="control-label col-sm-2">Name</label>
	<div class="col-sm-8">
	  <g:textField name="menuItem.name" class="form-control" value="${menuItem?.name}"/>
	</div>

</div>

<div class="form-group">
	<label for="menuItem.page.id" class="control-label col-sm-2">Page</label>
	<div class="col-sm-8">
		<spAdmin:pageSelect name="menuItem.page.id" class="form-control" value="${menuItem?.page?.id}" onchange="if(\$(this).val() != '') {\$('#spud_menu_item_url').val('');}" noSelection="['':'Use URL instead']"/>
	</div>
</div>

<div class="form-group">
	<label for="menuItem.url" class="control-label col-sm-2">Url</label>
	<div class="col-sm-8">
	  <g:textField name="menuItem.url" class="form-control" id="spud_menu_item_url" value="${menuItem?.url}"/>
	</div>
</div>

<div class="form-group">
	<label class="control-label col-sm-2" for="classes">Classes</label>
	<div class="col-sm-8">
	  <g:textField name="menuItem.classes" class="form-control" value="${menuItem?.classes}"/>
	</div>
</div>

<div class="form-group">
	<label class="control-label col-sm-2" for="menuItem.parentId">Parent Menu</label>
	<div class="col-sm-8">
		<spAdmin:menuItemSelect name="menuItem.parentId" class="form-control" value="${menuItem?.parentType == 'SpudMenuItem' ? menuItem.parentId : null}" menu="${menu}" filter="${menuItem?.id}"/>
	</div>

</div>

</fieldset>



