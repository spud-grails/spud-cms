<g:applyLayout name="spud/admin/detail" >

<content tag="data_controls">
  <g:link action="create" namespace="spud_admin" title="New User" class="btn btn-primary">New User</g:link>
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
          <td><g:link action="edit" id="${user.id}" title="Edit ${user.login}" namespace="spud_admin">${user.login}</g:link></td>

          <td>${user.email}</td>
          <td>${user.firstName}&nbsp;${user.lastName}</td>

          <td></td>
          <td>${user.dateCreated}</td>
          <td align="right">
            <g:link controller="user" namespace="spud_admin" action="delete" id="${user.id}" date-confirm="Are you sure you want to remove this user?" class="btn btn-danger">Remove</g:link>
          </td>
        </tr>
      </g:each>
    </tbody>
    <tfoot>
      <tr>
        <td colspan="6">
          <g:paginate controller="user" action="index" namespace="spud_admin" total="${userCount}" max="25" />
        </td>
      </tr>
    </tfoot>
  </table>
</content>

</g:applyLayout>



