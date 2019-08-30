<div id="page_partials_form" class="formtabs tab-content">
	<ul class="formtab_buttons nav nav-tabs">
	</ul>
	<g:each var="partial" in="${partials}">
		<div class="formtab tab-pane">
			<br/>
			<div class="form-group">
				<div class="col-sm-2 col-sm-offset-10">
					<spAdmin:formatterSelect name='partial.${partial.symbolName}.format' value="${partial.format}" class="pull-right input-sm form-control" data-formatter="spud-partial-editor-${partial.symbolName}"/>
				</div>
			</div>
			<g:hiddenField name="partial.${partial.symbolName}" value="${partial.name}" class="tab_name"/>
			<g:textArea name="partial.${partial.symbolName}.postContent" id="spud-partial-editor-${partial.symbolName}" data-content-css="${assetPath(src: 'spud/content.css')}" class="spud-formatted-editor form-control" value="${partial.postContent}" data-format="${partial.format}"/>
		</div>
	</g:each>
</div>
