function requestToFindUserDelete(id) {
    let url = 'http://localhost:8080/admin/user/' + id
    console.log(url)
    return $.ajax({
        method: 'GET',
        url: url,
        async: false,
        dataType: 'json',
        success: function (user) {
            drawDeleteModalWindow(user, id)
        },
        error: function (error) {
            console.log(error);
        }
    });
}


function requestToDeleteUser(id) {
    let url = 'http://localhost:8080/admin/user-delete/' + id
    console.log(url)
    return $.ajax({
        url: url,
        type: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
        },
        async: false,
        dataType: 'json',
        success: function (info) {
            console.log(info)
        },
        error: function (error) {
            console.log(error)
        }
    });
}

function drawDeleteModalWindow(user, id) {
    let modal = $('#placeModalDelete');
    console.log(user)
    let allRoles = ''
    for (let j = 0; j < user.roles.length; j++) {
        let roleStr = user.roles[j].role
        if (j != user.roles.length - 1) {
            allRoles += String(roleStr + ', ')
        } else {
            allRoles += String(roleStr)
        }
    }
    let body = `<div class="modal fade" id="modalDelete" tabindex="-1" role="dialog" aria-labelledby="modalDelete"
     aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header" id="headerDelete">
                <h5 class="modal-title" id="DeleteModalLabel">Delete user</h5>
                <button class="close" id="closeButtonKrest" type="button" data-dismiss="modal" aria-label="Close"></button>
                <span aria-hidden="true">&times;</span>
            </div>
            <div class="modal-body" id="deleteModal">
                <div class="container-fluid" id="deleteDiv">
                    <form id="deleteForm">
                        <div class="form-froup">
                            <label for="name">Username</label>
                            <input type="text" required class="form-control" id="name" value="${user.name}" readonly>
                        </div>
                        <div class="form-froup">
                            <label for="email">Email</label>
                            <input type="email" required class="form-control" id="email" value="${user.email}" readonly>
                        </div>
                        <div class="form-froup">
                            <label for="age">Age</label>
                            <input type="number" required class="form-control" id="age" value="${user.age}" readonly>
                        </div>
                        <div class="form-froup">
                            <label for="roles" class="com-form-label">Role:</label>
                            <select id="roles" class="form-control select" size="2" name="roles" style="max-height: 100px" disabled>
                                <option>${allRoles}</option>
                            </select>
                        </div>
                        <div class="modal-footer">
                            <button id="closeButton" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button id="deleteUserButton2" class="btn btn-danger" type="submit">Delete user</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>`
    modal.append(body)
    $('#deleteUserButton2').click(async () => {
        requestToDeleteUser(id)
    });
    $('#closeButtonKrest').click(async () => {
        let modalEdit = document.getElementById('modalDelete')
        var modalInstance = bootstrap.Modal.getInstance(modalEdit)
        modalInstance.hide();
        $('#modalDelete').remove()
    });
    $('#closeButton').click(async () => {
        let modalEdit = document.getElementById('modalDelete')
        var modalInstance = bootstrap.Modal.getInstance(modalEdit)
        modalInstance.hide();
        $('#modalDelete').remove()
    });

}