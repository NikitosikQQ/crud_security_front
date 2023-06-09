$('#addUserButton').click(async () => {
    let roleList = [
        {id: 1, role: "ROLE_USER"},
        {id: 2, role: "ROLE_ADMIN"}
    ]

    let addUserForm = $('#addUser')
    let name1 = addUserForm.find('#name').val().trim();
    let password1 = addUserForm.find('#password').val().trim();
    let email1 = addUserForm.find('#email').val().trim();
    let age1 = addUserForm.find('#age').val().trim();
    let vkOwnerId1 = addUserForm.find('#vkOwnerId').val().trim();
    let checkedRoles = () => {
        let array = []
        let options = document.querySelector('#roles').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                array.push(roleList[i])
            }
        }
        return array;
    }
    if (!(name1 === '') && !(password1 === '') && !(email1 === '') && age != 0 && vkOwnerId1 != 0 && checkedRoles().length != 0) {
        let data = {
            name: name1,
            password: password1,
            age: age1,
            vkOwnerId: vkOwnerId1,
            email: email1,
            roles: checkedRoles()
        }
        requestToCreateUser(data);
    }


});

function requestToCreateUser(data) {

    return $.ajax({
        url: "http://localhost:8080/admin/user-create",
        type: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: JSON.stringify(data),
        async: false,
        dataType: 'json',
        success: function () {
        },
        error: function (error) {
            console.log(error)
            showErrorCreate(error.responseText)
        }
    });
}

function showErrorCreate(error) {
    let errorInfo = JSON.parse(error)
    console.log(error)
    console.log(errorInfo)
    let alert = `<div class="alert alert-danger alert-dismissible fade show col-12" role="alert" id="messageError">
                            ${errorInfo.message}
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>`;
    $('#bodyCreateModal').prepend(alert);
    $('#exampleModal').show();

}
