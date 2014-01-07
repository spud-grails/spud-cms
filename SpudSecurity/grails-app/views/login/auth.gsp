<html>
<head>
	<meta name='layout' content='spud/login'/>
	<title>Spud Login</title>
</head>

<body>
	<div class="login-form">
		<h1>Spud CMS Admin</h1>
		<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off'>
		  <g:if test="${flash.notice}">
			  <div class="flash notice">
			    ${flash.notice}
			  </div>
			</g:if>
			<g:if test="${flash.warning}">
			  <div class="flash warning">
			    ${flash.warning}
			  </div>
			</g:if>
			<g:if test="${flash.message}">
			  <div class="flash warning">
			    ${flash.message}
			  </div>
			</g:if>
			<g:if test="${flash.error}">
			  <div class="flash error">
			  	  ${flash.error}
			  </div>
			</g:if>

		  <div class="field-group">
		  	<label for='username'><g:message code="springSecurity.login.username.label"/>:</label>
				<input type='text' class='text_' name='j_username' id='username'/>
		  </div>
		  <div class="field-group">
				<label for='password'><g:message code="springSecurity.login.password.label"/>:</label>
				<input type='password' class='text_' name='j_password' id='password'/>
		  </div>
		  <div class="field-group">
		  	<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me' <g:if test='${hasCookie}'>checked='checked'</g:if>/>
				<label for='remember_me' class="inline"><g:message code="springSecurity.login.remember.me.label"/></label>
		  </div>
		  <div class="field-group">
				<input type='submit' id="submit" value='${message(code: "springSecurity.login.button")}'/>
		  </div>

		</form>
	</div>
<script type='text/javascript'>
	<!--
	(function() {
		document.forms['loginForm'].elements['j_username'].focus();
	})();
	// -->
</script>
</body>
</html>
