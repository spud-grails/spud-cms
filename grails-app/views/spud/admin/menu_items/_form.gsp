<fieldset>
<div class="control-group">
	<label for="menuItem.name" class="control-label">Name</label>
	<div class="controls">
	  <g:textField name="menuItem.name" value="${menuItem?.name}"/>
	</div>

</div>

<div class="control-group">
	<label for="menuItem.page.id" class="control-label">Page</label>
	<div class="controls">
		<g:select name="menuItem.page.id" from="${pageOptions}" value="${menuItem?.page?.id}" optionKey="value" optionValue="name" noSelection="['':'Use URL instead']" onchange="if(\$(this).val() != '') {\$('#spud_menu_item_url').val('');}"/>
	</div>
</div>

<div class="control-group">
	<label for="menuItem.url" class="control-label">Url</label>
	<div class="controls">
	  <g:textField name="menuItem.url" value="${menuItem?.url}"/>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="classes">Classes</label>
	<div class="controls">
	  <g:textField name="menuItem.classes" value="${menuItem?.classes}"/>
	</div>
</div>

<div class="control-group">
	<label class="control-label" for="menuItem.parentId">Parent Menu</label>
	<div class="controls">
		<g:select name="menuItem.parentId" from="${menuParentOptions}" value="${menuItem?.parentType == 'SpudMenuItem' ? menuItem.parentId : null}" optionKey="value" optionValue="name" noSelection="['':menu.name]"/>
	</div>

</div>

</fieldset>



