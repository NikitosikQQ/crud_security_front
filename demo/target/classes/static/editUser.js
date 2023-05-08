function requestToFindUserEdit(id) {
    let url = 'http://localhost:8080/admin/user/' + id
    console.log(url)
    return $.ajax({
        method: 'GET',
        url: url,
        async: false,
        dataType: 'json',
        success: function (user) {
            drawEditModalWindow(user)
        },
        error: function (error) {
        }
    });
}


function requestToEditUser(user) {
    return $.ajax({
        url: 'http://localhost:8080/admin/user-update/',
        type: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        async: false,
        data: JSON.stringify(user),
        dataType: 'json',
        success: function (info) {
            console.log(info)
        },
        error: function (error) {
            showErrorEdit(error.responseText)
        }
    });
}

function drawEditModalWindow(user) {
    let modal = $('#placeModalEdit');
    console.log(user)
    let body = `<div class="modal fade" id="modalEdit" tabindex="-1" role="dialog" aria-labelledby="modalEdit"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="EditModalLabel">Edit user</h5>
                <button class="close" id="closeButtonKrest2" type="button" data-dismiss="modal" aria-label="Close"></button>
                <span aria-hidden="true">&times;</span>
            </div>
            <div class="modal-body" id="editModal">
                <div class="container-fluid" id="editDiv">
                    <form id="editForm">
                        <div class="form-froup">
                            <label for="id">ID</label>
                            <input type="text" required class="form-control" id="idEdit" value="${user.id}" readonly>
                        </div>
                        <div class="form-froup">
                            <label for="name">Username</label>
                            <input type="text" required class="form-control" id="nameEdit" value="${user.name}" >
                        </div>
                        <div class="form-froup">
                            <label for="age">Password</label>
                            <input type="password" required class="form-control" id="passwordEdit" value="${user.password}" >
                        </div>
                        <div class="form-froup">
                            <label for="emailEdit">Email</label>
                            <input type="email" required class="form-control" id="emailEdit" value="${user.email}" aria-describedby="emailHelp">
                        </div>
                        <div class="form-froup">
                            <label for="age">Age</label>
                            <input type="number" required class="form-control" id="ageEdit" value="${user.age}" >
                        </div>
                        <div class="form-froup">
                            <label for="Newroles" class="com-form-label">New Roles:</label>
                            <br/>
                            <select id="Newroles"  required class="form-control select" size="5" multiple="multiple" >
                                <option value="ROLE_USER">User</option>
                                <option value="ROLE_ADMIN">Admin</option>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <button id="closeButton2" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button id="editUserButton2" class="btn btn-success" type="submit">Edit user</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>`

    modal.append(body)

    let roleList = [
        {id: 1, role: "ROLE_USER"},
        {id: 2, role: "ROLE_ADMIN"}
    ]


    $('#editUserButton2').click(async () => {

        let editUserForm = $('#editForm')
        let id1 = editUserForm.find('#idEdit').val();
        let name1 = editUserForm.find('#nameEdit').val();
        let email1 = editUserForm.find('#emailEdit').val();
        let password1 = editUserForm.find('#passwordEdit').val();
        let age1 = editUserForm.find('#ageEdit').val();
        let checkedRoles = () => {
            let array = []
            let options = document.querySelector('#Newroles').options
            for (let i = 0; i < options.length; i++) {
                if (options[i].selected) {
                    array.push(roleList[i])
                }
            }
            return array;
        }

        if (!(name1 === '') && !(email1 === '') && age1 != 0 && checkedRoles().length != 0 && !(password1 === '')) {
            let data = {
                id: id1,
                name: name1,
                age: age1,
                password: password1,
                email: email1,
                roles: checkedRoles()
            }
            console.log(id1, name1, email1, age1)
            requestToEditUser(data);
        }
    });
    $('#closeButtonKrest2').click(async () => {
        let modalEdit = document.getElementById('modalEdit')
        var modalInstance = bootstrap.Modal.getInstance(modalEdit)
        modalInstance.hide();
        $('#modalEdit').remove()
    });
    $('#closeButton2').click(async () => {
        let modalEdit = document.getElementById('modalEdit')
        var modalInstance = bootstrap.Modal.getInstance(modalEdit)
        modalInstance.hide();
        $('#modalEdit').remove()
    });

}

function showErrorEdit (error){
    let errorInfo = JSON.parse(error)
    console.log(error)
    console.log(errorInfo)
    let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${errorInfo.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
    $('#editModal').prepend(alert);
    $('#modalEdit').show();

}