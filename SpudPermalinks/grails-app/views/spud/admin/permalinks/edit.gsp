<g:applyLayout name="spud/admin/detail" >

<content tag="detail">
	<g:form name="edit_permalink" url="[action: 'show',controller: 'permalinks',namespace: 'spud_admin', id:permalink.id]" id="${permalink.id}" method="put" class="form-horizontal">
		<g:render template="/spud/admin/permalinks/form" model="[permalink: permalink]" />

		<div class="form-actions">
			<g:submitButton name="_submit" value="Save Permalink" class="btn btn-primary"/>
	  </div>
	</g:form>

</content>

</g:applyLayout>
