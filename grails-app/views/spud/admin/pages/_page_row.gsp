<div class="page_row">

	<span class="row_meta">
		<spAdmin:link resource="pages" action="edit" id="${page.id}">${page.name}</spAdmin:link> ${!page.published ? '(unpublished)' : ''}
	</span>

	<span class="edit_controls">
		<spAdmin:link action="show" id="${page.id}" title="Preview" class="btn btn-sm btn-link" target="_blank">Preview</spAdmin:link>
		<spAdmin:link resource="pages" data-method="DELETE" action="delete" id="${page.id}" title="Remove" data-confirm="Are you sure you want to remove this page?" class="btn btn-sm btn-danger"><span class="glyphicon glyphicon-trash"></span></spAdmin:link>
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

