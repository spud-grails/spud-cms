<g:applyLayout name="spud/admin/detail" >

<content tag="detail">
	<g:form name="new_permalink" url="[action: 'index',controller: 'permalinks',namespace: 'spud_admin']" method="POST" class="form-horizontal">
		<g:render template="/spud/admin/permalinks/form" model="[permalink: permalink]" />

		<div class="form-actions">
			<g:submitButton name="_submit" value="Create Permalink" class="btn btn-primary"/>
	  </div>
	</g:form>

</content>

</g:applyLayout>
