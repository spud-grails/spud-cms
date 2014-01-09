<div id="page_partials_form" class="formtabs tab-content">
	<ul class="formtab_buttons nav nav-tabs">
	</ul>
	<g:each var="partial" in="${partials}">
		<div class="formtab tab-pane">
			<g:hiddenField name="partial.${partial.symbolName}.name" value="${partial.name}" class="tab_name"/>
			<g:textArea name="partial.${partial.symbolName}.content" class="tinymce" value="${partial.content}"/>
		</div>
	</g:each>
</div>
