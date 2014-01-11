<fieldset>
  <label for="snippet.name" style="display:none;" class="control-label">Name</label>
  <g:textField name="snippet.name" class="full-width" value="${snippet?.name}" placeholder="Enter title here"/>
</fieldset>
<br/>
<div>
  <div style="clear:both;">
    <g:textArea name="snippet.content" class="spud-formatted-editor full-width tinymce" style="width:100%;" value="${snippet?.content}"/>
  </div>
</div>
