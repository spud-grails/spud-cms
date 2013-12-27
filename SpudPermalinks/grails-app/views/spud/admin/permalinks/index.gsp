<g:applyLayout name="spud/admin/detail" >

<content tag="data_controls">
  <g:link action="create" namespace="spud_admin" title="New Permalink" class="btn btn-primary">New Permalink</g:link>
</content>
<content tag="detail">
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
            <g:link action="edit" id="${permalink.id}" title="Edit ${permalink.urlName}" namespace="spud_admin" class="btn">Edit</g:link>
            <g:link resource="permalinks" namespace="spud_admin" data-method="delete" id="${permalink.id}" date-confirm="Are you sure you want to remove this permalink?" class="btn btn-danger">Remove</g:link>
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



