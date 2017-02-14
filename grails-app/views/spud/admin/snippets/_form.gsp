<fieldset>
<div class="form-group">
  <label for="snippet.name" style="display:none;" class="control-label">Name</label>
  <div class="col-sm-12 ">
	  <g:textField name="snippet.name" class="full-width form-control" value="${snippet?.name}" placeholder="Enter title here"/>
  </div>
  </div>
</fieldset>

<div class="form-group">
  <div class="col-sm-12">
	<div class="clearfix formtab">
      <div class="form-group">
        <div class="col-sm-2 col-sm-offset-10">
          <spAdmin:formatterSelect name='snippet.format' value="${snippet.format}" class="pull-right input-sm form-control" data-formatter="spud-editor-${snippet.name?.parameterize()}"/>
        </div>
      </div>

      <g:textArea name="snippet.postContent" id="spud-editor-${snippet.name?.parameterize()}" class="spud-formatted-editor form-control" data-content-css="${assetPath(src: 'spud/content.css')}" style="width:100%" value="${snippet.postContent}" data-format="${snippet.format}" />
	</div>
  </div>
</div>
