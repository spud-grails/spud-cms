<g:applyLayout name="spud/admin/detail" >

<content tag="detail">
	<g:form name="new_user" url="[action: 'index',controller: 'user',namespace: 'spud_admin']" method="POST" class="form-horizontal">
		<g:render template="/spud/admin/users/form" model="[user: user]" />

		<div class="form-actions">
			<g:submitButton name="_submit" value="Create User" class="btn btn-primary"/>
	  </div>
	</g:form>

</content>

</g:applyLayout>
