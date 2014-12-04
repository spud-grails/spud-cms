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
	    <g:textArea name="snippet.postContent" class="spud-formatted-editor full-width" data-content-css="${assetPath(src: 'spud/content.css')}" style="width:100%;" value="${snippet?.postContent}"/>
	</div>
  </div>
</div>
