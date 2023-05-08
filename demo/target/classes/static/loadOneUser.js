function loadOneUser() {
    $.ajax({
        method: 'GET',
        url: "http://localhost:8080/user",
        async: false,
        dataType: 'json',
        success: function (user) {
            console.log(user)
            drawTableUser(user);
        },
        error: function (error) {
            console.log(error);
        }
    });
}

function drawTableUser(user) {

    let allRoles = ''
    for (let j = 0; j < user.roles.length; j++) {
        let roleStr = user.roles[j].role
        if (j != user.roles.length - 1) {
            allRoles += String(roleStr + ', ')
        } else {
            allRoles += String(roleStr)
        }
    }

    let mainDiv = $('#mainDiv')
    mainDiv.replaceWith(`<div id="mainDiv">
<h1 style="color: black">My account</h1>
    <br/>
    <table class="table table-bordered table-hover">
        <thead class="thead-dark">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Age</th>
            <th>Role</th>
        </tr>
        </thead>
        <thead id="userTable" class="thead-light">
        <tr>
            <td class="table-active">${user.id}</td>
            <td class="table-active">${user.username}</td>
            <td class="table-active">${user.email}</td>
            <td class="table-active">${user.age}</td>
            <td class="table-active">${allRoles} </td>
        </tr>
        </thead>
        </table>
</div>`)
}