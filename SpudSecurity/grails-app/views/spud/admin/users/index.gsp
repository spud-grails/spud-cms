<g:applyLayout name="spud/admin/detail" >

<content tag="data_controls">
  <spAdmin:link action="create" title="New User" class="btn btn-primary">New User</spAdmin:link>
</content>
<content tag="detail">
    <table class="admin-table data-table" id="usertable">
    <thead>
      <tr>
        <th>Login</th>
        <th>Email</th>
        <th>Name</th>

        <th>Last Login</th>
        <th>Date Created</th>
        <th></th>
      </tr>
    </thead>
    <tbody>
      <g:each var="user" in="${users}">

        <tr id="row_user_<%=user.id%>">
          <td><spAdmin:link action="edit" id="${user.id}" title="Edit ${user.login}">${user.login}</spAdmin:link></td>

          <td>${user.email}</td>
          <td>${user.firstName}&nbsp;${user.lastName}</td>

          <td></td>
          <td>${user.dateCreated}</td>
          <td align="right">
            <spAdmin:link controller="user" action="delete" id="${user.id}" date-confirm="Are you sure you want to remove this user?" class="btn btn-danger">Remove</spAdmin:link>
          </td>
        </tr>
      </g:each>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="6">
          <g:paginate resource="user" action="index" namespace="spud_admin" total="${userCount}" max="25" />
        </td>
      </tr>
    </tfoot>
  </table>
</content>

</g:applyLayout>



