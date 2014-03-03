<g:applyLayout name="spud/admin/detail" >

<content tag="data_controls">
  <spAdmin:link action="create" title="New Page" class="btn btn-sm btn-primary">New Page</spAdmin:link>
  <spAdmin:link action="clear" title="Clear Page Cache" class="btn btn-sm btn-warning">Clear Cache</spAdmin:link>
</content>
<content tag="detail">
  <div class="page_list">
    <g:each var="page" in="${pages}">
      <g:render template="/spud/admin/pages/page_row" model="[page: page]"/>
    </g:each>
  </div>
</content>

</g:applyLayout>


