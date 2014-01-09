<g:applyLayout name="spud/admin/detail" >

	<content tag="detail">
		<g:form name="new_snippet" url="[action: 'save', method:'POST', resource: 'snippets',namespace: 'spud_admin']" method="POST" class="form-horizontal">
			<g:render template="/spud/admin/snippets/form" model="[snippet: snippet]" />
			<div class="form-actions">
				<g:submitButton name="_submit" value="Create Snippet" class="btn btn-primary"/> or <spAdmin:link action="index" resource="snippets" class="btn">cancel</spAdmin:link>
		  </div>
		</g:form>
	</content>
</g:applyLayout>
