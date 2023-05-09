function loadUsers() {
    $.ajax({
        method: 'GET',
        url: "http://localhost:8080/admin/users",
        async: false,
        dataType: 'json',
        success: function (users) {
            drawTableUsers(users);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function drawTableUsers(users) {
    let mainDiv = $('#mainDiv')

    mainDiv.html(`
<h1 style="color: black">All users</h1>
    <br/>
    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>VK ID</th>
            <th>Count of vk video</th>
            <th>Age</th>
            <th>Role</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
<thead id="usersTable" class="thead-light">
   </thead>`)
    for (let i = 0; i < users.length; i++) {
        let allRoles = ''
        for (let j = 0; j < users[i].roles.length; j++) {
            let roleStr = users[i].roles[j].role
            if (j != users[i].roles.length - 1) {
                allRoles += String(roleStr + ', ')
            } else {
                allRoles += String(roleStr)
            }
        }
        $("#usersTable").append(`

        <tr>
            <td class="table-active">${users[i].id}</td>
            <td class="table-active">${users[i].username}</td>
            <td class="table-active">${users[i].email}</td>
            <td class="table-active">${users[i].vkOwnerId}</td>
            <td class="table-active">${users[i].countOfVideo}</td>
            <td class="table-active">${users[i].age}</td>
            <td class="table-active">${allRoles} </td>
            <td class="table-active"><button type="submit" class="btn btn-primary" onclick="requestToFindUserEdit(${users[i].id});"  data-toggle="modal" data-target="#modalEdit">Edit</button></td>
            <td class="table-active"><button type="submit" class="btn btn-danger" onclick="requestToFindUserDelete(${users[i].id});" data-toggle="modal" data-target="#modalDelete">Delete</button></td>
        </tr>
     `)

    }
}