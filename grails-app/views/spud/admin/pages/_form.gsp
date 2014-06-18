<g:renderErrors bean="${page}" />
<fieldset>
	<legend>Page Title</legend>
		<div class="form-group">
		<label for="page.name" style="display:none;" class="control-label">Name</label>
		<div class="col-sm-12">
			<g:textField name="page.name" class="form-control full-width" value="${page?.name}" autofocus="true"/>	
		</div>
		
	</div>
</fieldset>


<g:render template="/spud/admin/pages/page_partials_form" model="[partials: partials]"/>

<br />
<fieldset>
	<legend>Advanced Settings (optional)</legend>
	<div class="form-group">
		<label for="page.layout" class="control-label col-sm-2">Template</label>
		<div class="col-sm-8">
			
				<g:select name="page.layout" class="form-control" from="${layouts}" value="${page?.layout}" optionKey="layout" optionValue="name" id="spud_page_layout" data-source="${createLink([controller: 'pages', namespace: 'spud_admin', action:'pageParts'] + (page?.id ? [id: page.id] : [:]))}"/>
			
			<span class="help-inline">Use this to control the layout template to be used for this page if they are available.</span>
		</div>
	</div>

	<div class="form-group">
		<label for="page.spudPage.id" class="control-label col-sm-2">Parent Page</label>

		<div class="col-sm-8">
			<spAdmin:pageSelect class="form-control" name="page.spudPage.id" filter="${page?.id}" value="${page?.spudPageId}" noSelection="['':'']"/>
			%{-- TODO DROPDOWN BOX TO SELECT A PARENT PAGE GOES HERE --}%
		</div>
	</div>

		<div class="form-group">
			<label for="page.urlName" class="control-label col-sm-2">Perma Link</label>

			<div class="col-sm-8">
				<g:textField name="page.urlName" value="${page?.urlName}" title="" size="20" id="spud_page_url_name" class="form-control" disabled="${page?.useCustomUrlName ? null : true}"/>
				<div class="checkbox">
					<label >
						<g:checkBox name="page.useCustomUrlName" onchange="\$('#spud_page_url_name').attr('disabled',!this.checked);" checked="${page?.useCustomUrlName}"/>
						Customize this pages url
					</label>
				</div>
			</div>
		</div>
</fieldset>

<fieldset>
	<legend>Meta Information (optional)</legend>
	<p>These fields are used to notify search engines about important keywords and the appropriate description to display in a search result.</p>

		<div class="form-group">
			<label for="page.metaKeywords" class="control-label col-sm-2">Keywords</label>
			<div class="col-sm-8">
				<g:textField name="page.metaKeywords" class="form-control" value="${page?.metaKeywords}"/>
				<span class="help-inline">A Comma seperated list of keywords for search engines. Keep it short (no more than 10 keywords)</span>
			</div>

		</div>
		<div class="form-group">
			<label for="page.metaDescription" class="control-label col-sm-2">Description</label>

			<div class="col-sm-8">
				<g:textArea name="page.metaDescription" style="height:60px;" class="form-control" value="${page?.metaDescription}"/>
				<span class="help-inline">A short description of the page. This is what appears on a search engines search result page.</span>
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="page.visibility">Visibility</label>
			<div class="col-sm-8">
				%{-- <%=f.select :visibility, [["Public",0],["Private",1]]%> --}%
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-8 col-sm-offset-2">
				<div class="checkbox">
					 <label>
					 	<g:checkBox name="page.published" value="${page?.published}"/> Published
				 	</label>
				</div>
				
			</div>
		</div>
		<div class="form-group">
			<label for="page.notes" class="control-label col-sm-2">Notes</label>
			<div class="col-sm-8">
				<g:textArea name="page.notes" style="height:60px;" class="form-control" value="${page?.notes}"/>
				<span class="help-inline">Have a note to make about this page while you work? Place it here for later.</span>
			</div>
		</div>
	<g:hiddenField name="preview" value="0"/>
</fieldset>
