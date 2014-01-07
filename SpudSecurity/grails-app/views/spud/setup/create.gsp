<g:applyLayout name="spud/setup">
	<content tag="detail">
		<g:form name="new_user" url="[action: 'save',controller: 'setup',namespace: 'spud_admin', method: 'POST']" method="POST" class="form-horizontal">

		<fieldset>
			<legend>New Admin Account</legend>

				<div class="control-group">
					<label for="login" class="control-label">Login</label>

					<div class="controls">
						<g:textField name="login" value="${user?.login}" size="25" />
					</div>
				</div>
				<div class="control-group">
					<label for="email" class="control-label">Email</label>
					<div class="controls">
						<g:textField name="email" value="${user?.login}" size="25" />
					</div>
				</div>

				<div class="control-group">
					<label for="password" class="control-label">Password</label>
					<div class="controls">
						<g:passwordField name="password" size="25" />
						<p class="help-block">Password must be at least 8 characters</p>
					</div>
				</div>
				<div class="control-group">
					<label for="passwordConfirmation" class="control-label">Confirm</label>
					<div class="controls">
						<g:passwordField name="passwordConfirmation" size="25" />
						<p class="help-block">Retype your password here.</p>
					</div>
				</div>
		</fieldset>
		<div class="form-actions">
			<g:submitButton name="_submit" value="Create Admin Account" class="btn btn-primary"/>
	  </div>
	</g:form>

	</content>
</g:applyLayout>
