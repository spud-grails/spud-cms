<g:renderErrors bean="${page}" />

<fieldset>
	<div class="control-group">
		<legend>Page Title</legend>
		<label for="page.name" style="display:none;" class="control-label">Name</label>
		<g:textField name="page.name" class="full-width" value="${page?.name}"/>
	</div>
</fieldset>

<div id="page_partials_form" class="formtabs tab-content">
	<ul class="formtab_buttons nav nav-tabs">
	</ul>
	<g:each var="partial" in="${partials}">
		<div class="formtab tab-pane">
			<g:hiddenField name="partialName" value="${partial.name}" class="tab_name"/>
			<g:textArea name="partial.${partial.name}" class="tinymce" value="${partial.content}"/>
		</div>
	</g:each>
</div>

<br />
<fieldset>
	<legend>Advanced Settings (optional)</legend>

			<div class="control-group">
				<label for="page.layout" class="control-label">Template</label>
				<div class="controls">
					<g:select name="page.layout" from="${layouts}" value="${page?.layout}" optionKey="name" optionValue="name"/>

					<span class="help-inline">Use this to control the layout template to be used for this page if they are available.</span>
				</div>
			</div>

		<div class="control-group">
			<label for="page.spudPage" class="control-label">Parent Page</label>

			<div class="controls">
				%{-- TODO DROPDOWN BOX TO SELECT A PARENT PAGE GOES HERE --}%
			</div>
		</div>

		<div class="control-group">
			<label for="page.urlName" class="control-label">Perma Link</label>

			<div class="controls">
				/<g:textField name="page.urlName" value="${page?.urlName}" title="" size="20" id="spud_page_url_namme"/>
				<label class="checkbox inline">
					<g:checkBox name="page.useCustomUrlName" onchange="\$('#spud_page_url_name').attr('disabled',!this.checked);"/>
					Customize this pages url
				</label>
			</div>
		</div>
</fieldset>

<fieldset>
	<legend>Meta Information (optional)</legend>
	<p>These fields are used to notify search engines about important keywords and the appropriate description to display in a search result.</p>

		<div class="control-group">
			<label for="page.metaKeywords" class="control-label">Keywords</label>
			<div class="controls">
				<g:textField name="page.metaKeywords" style="width:600px;" value="${page?.metaKeywords}"/>
				<span class="help-inline">A Comma seperated list of keywords for search engines. Keep it short (no more than 10 keywords)</span>
			</div>

		</div>
		<div class="control-group">
			<label for="page.metaDescription" class="control-label">Description</label>

			<div class="controls">
				<g:textArea name="page.metaDescription" style="width:600px;height:40px;" value="${page?.metaDescription}"/>
				<span class="help-inline">A short description of the page. This is what appears on a search engines search result page.</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="page.visibility">Visibility</label>
			<div class="controls">
				%{-- <%=f.select :visibility, [["Public",0],["Private",1]]%> --}%
			</div>
		</div>
		<div class="control-group">
			<label for="page.published" class="control-label">Published</label>
			<div class="controls">
				<g:checkBox name="page.published" value="${page?.published}"/>
			</div>
		</div>
		<div class="control-group">
			<label for="page.notes" class="control-label"Notes></label>
			<div class="controls">
				<g:textArea name="page.notes" style="width:400px;height:40px;" value="${page?.notes}"/>
				<span class="help-inline">Have a note to make about this page while you work? Place it here for later.</span>
			</div>
		</div>
	<g:hiddenField name="preview" value="0"/>
</fieldset>

