$(document).ready(function(){

    $.ajax({
        method: 'GET',
        url: "http://localhost:8080/user",
        async: false,
        dataType: 'json',
        success: function (user) {
            let allRoles = ''
            for (let j = 0; j < user.roles.length; j++) {
                let roleStr = user.roles[j].role
                if (j != user.roles.length - 1) {
                    allRoles += String(roleStr + ', ')
                } else {
                    allRoles += String(roleStr)
                }
            }
            $('#infoAccName').append(user.name);
            $('#infoAccRoles').append(allRoles);
        },
        error: function (error) {
            console.log(error);
        }
    });
});
