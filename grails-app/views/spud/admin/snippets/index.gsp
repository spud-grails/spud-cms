<g:applyLayout name="spud/admin/detail" >
  <content tag="data_controls">
    <spAdmin:link resource="snippets" action="create" class="btn btn-primary" title="New Snippet">New Snippet</spAdmin:link>
  </content>
  <content tag="detail">
    <p class="lead">Snippets are reusable chunks of content that can be injected into your pages and layouts.</p>
    <div class="page_list">
        <g:each var="snippet" in="${snippets}">
          <div class="page_row">
            <span class="row_meta"><spAdmin:link resource="snippets" id="${snippet.id}" action="edit">${snippet.name}</spAdmin:link>
            <span class="edit_controls">
              <spAdmin:link class="btn btn-danger" resource="snippets" id="${snippet.id}" action="delete" data-method="DELETE" data-confirm="Are you sure you want to remove this snippet?">Remove</spAdmin:link>
            </span>
            <br style="clear:both;"/>
          </div>
        </g:each>
        <g:paginate resource="snippets" action="index" namespace="spud_admin" total="${snippetCount}" max="25" />
    </div>

  </content>
</g:applyLayout>
