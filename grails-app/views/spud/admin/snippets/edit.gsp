<g:applyLayout name="spud/admin/detail" >

	<content tag="detail">
		<g:form name="edit_snippet" url="[action: 'update', method:'PUT', resource: 'snippets',namespace: 'spud_admin', id: snippet.id]" method="PUT" class="form-horizontal">
			<g:render template="/spud/admin/snippets/form" model="[snippet: snippet]" />
			<div class="col-sm-12">
				<g:submitButton name="_submit" value="Save Snippet" class="btn btn-primary"/> or <spAdmin:link action="index" resource="snippets" class="btn btn-default">cancel</spAdmin:link>
		  </div>
		</g:form>
	</content>
</g:applyLayout>
