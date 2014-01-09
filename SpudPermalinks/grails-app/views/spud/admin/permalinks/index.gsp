<g:applyLayout name="spud/admin/detail" >

<content tag="data_controls">
  <spAdmin:link action="create" title="New Permalink" class="btn btn-primary">New Permalink</spAdmin:link>
</content>

<content tag="detail">
  <p class="lead">Permalinks allow you to define a url that can be redirected to another location. Useful for moving pages and content without sacrificing SEO.</p>
    <table class="admin-table data-table" id="usertable">
    <thead>
      <tr>
        <th>Source Url</th>
        <th>Destination Url</th>
        <th>Attachment</th>
        <th>Date Created</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <g:each var="permalink" in="${permalinks}">
        <tr>
          <td>${permalink.urlName}</td>

          <td>${permalink.destinationUrl}</td>
          <td>${permalink.attachmentType ?: 'N/A'}</td>
          <td>${permalink.dateCreated}</td>
          <td align="right">
            <g:if test="${!permalink.attachmentType}">
              <spAdmin:link action="edit" id="${permalink.id}" title="Edit ${permalink.urlName}" class="btn">Edit</spAdmin:link>
            </g:if>
            <spAdmin:link resource="permalinks" action="delete" data-method="DELETE" id="${permalink.id}" data-confirm="Are you sure you want to remove this permalink?" class="btn btn-danger">Remove</spAdmin:link>
          </td>
        </tr>
      </g:each>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="6">
          <g:paginate controller="permalinks" action="index" namespace="spud_admin" method="delete" total="${permalinkCount}" max="25" />
        </td>
      </tr>
    </tfoot>
  </table>
</content>

</g:applyLayout>



