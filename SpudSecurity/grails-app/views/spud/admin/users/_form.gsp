<fieldset>
	<legend>User Details</legend>
	<div class="control-group">
		<label for="user.login" class="control-label required">Login</label>
		<div class="controls">
			<g:textField name="user.login" value="${user.login}" size="25" />
		</div>
	</div>

	<div class="control-group">
		<label for="user.email" class="control-label required">Email</label>
		<div class="controls">
			<g:textField name="user.email" value="${user.email}" size="25" />
		</div>
	</div>

	<div class="control-group">
		<label for="user.firstName" class="control-label required">First Name</label>
		<div class="controls">
			<g:textField name="user.firstName" value="${user.firstName}" size="25" />
		</div>
	</div>

	<div class="control-group">
		<label for="user.lastName" class="control-label required">Last Name</label>
		<div class="controls">
			<g:textField name="user.lastName" value="${user.lastName}" size="25" />
		</div>
	</div>

</fieldset>
<fieldset>
	<legend>Credentials</legend>
	<div class="control-group">
		<label for="user.password" class="control-label required">Password</label>
		<div class="controls">
			<g:passwordField name="user.password" size="25" />
			<p class="help-block">Password must be at least 8 characters</p>
		</div>
	</div>

	<div class="control-group">
		<label for="user.passwordConfirmation" class="control-label required">Confirm</label>
		<div class="controls">
			<g:passwordField name="user.passwordConfirmation" size="25" />
			<p class="help-block">Retype your password here.</p>
		</div>
	</div>
</fieldset>
<fieldset>
	<legend>Roles</legend>
	<div class="control-group">
		<label for="user.superAdmin" class="control-label required">Super Admin</label>
		<div class="controls">
			<g:checkBox name="user.superAdmin" checked="${user.superAdmin}" />
		</div>
	</div>
</fieldset>
