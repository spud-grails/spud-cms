<div class="page_row">

	<span class="row_meta">
		<spud:adminLink resource="pages" action="edit" id="${page.id}">${page.name}</spud:adminLink>
	</span>

	<span class="edit_controls">
		<spud:adminLink action="preview" id="${page.id}" title="Preview" class="btn">Preview</spud:adminLink>
		&nbsp;&nbsp;
		<spud:adminLink action="delete" id="${page.id}" title="Remove" data-confirm="Are you sure you want to remove this page?" class="btn btn-danger">Remove</spud:adminLink>
	</span>
	<br style="clear:both;"/>
</div>
<g:if test="${page.pages.size() > 0}">
	<div class="left_guide">
		<g:each var="subPage" in="${page.pages}">
			<g:render template="/spud/admin/pages/page_row" model="[page:subPage]"/>
		</g:each>
	</div>
</g:if>

