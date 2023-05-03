$(document).ready(function () {
    loadUsers()
});

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
    for (let i = 0; i < users.length; i++) {
        $("#users").append(`<tr>`)
        let html = `
            <td className="table-active">${users[i].id}</td>
            <td className="table-active">${users[i].username}</td>
            <td className="table-active">${users[i].email}</td>
            <td className="table-active">${users[i].age}</td>`
        $("#users").append(html)
        let allRoles = ''
        for (let j = 0; j < users[i].roles.length; j++) {
            let roleStr = users[i].roles[j].role
            if (j != users[i].roles.length - 1) {
                allRoles += String(roleStr + ', ')
            } else {
                allRoles += String(roleStr)
            }
        }
        html = `<td className="table-active"> ${allRoles} </td>`
        $("#users").append(html)
        $("#users").append(`<td class="table-active"><button type="submit" class="btn btn-primary"  onclick="requestToFindUserEdit(${users[i].id});"  data-toggle="modal" data-target="#modalEdit">Edit</button></td>
            <td class="table-active"><button type="submit" class="btn btn-danger"   onclick="requestToFindUserDelete(${users[i].id});" data-toggle="modal" data-target="#modalDelete">Delete</button></td>`)
        $("#users").append(`</tr>`)
    }

}