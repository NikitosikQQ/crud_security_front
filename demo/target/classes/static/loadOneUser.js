$(document).ready(function () {
    loadOneUser()
});


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
    $("#userTable").append(`<tr>`)
    let html = `
            <td className="table-active">${user.id}</td>
            <td className="table-active">${user.username}</td>
            <td className="table-active">${user.email}</td>
            <td className="table-active">${user.age}</td>
            `
    $("#userTable").append(html)
    let allRoles = ''
    for (let j = 0; j < user.roles.length; j++) {
        let roleStr = user.roles[j].role
        if (j != user.roles.length - 1) {
            allRoles += String(roleStr + ', ')
        } else {
            allRoles += String(roleStr)
        }
    }
    html = `<td className="table-active"> ${allRoles} </td>`
    $("#userTable").append(html)
    $("#userTable").append(`</tr>`)


}