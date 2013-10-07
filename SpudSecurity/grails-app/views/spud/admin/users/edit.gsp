<g:applyLayout name="spud/admin/detail" >

<content tag="detail">
	<g:form name="edit_user" url="[action: 'show',controller: 'user',namespace: 'spud_admin', id:user.id]" id="${user.id}" method="put" class="form-horizontal">
		<g:render template="/spud/admin/users/form" model="[user: user]" />

		<div class="form-actions">
			<g:submitButton name="_submit" value="Save User" class="btn btn-primary"/>
	  </div>
	</g:form>

</content>

</g:applyLayout>
