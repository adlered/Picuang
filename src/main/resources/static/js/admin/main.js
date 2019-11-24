$(function () {
    axios.get('/api/admin/init')
        .then(function (response) {
                if (response.data.code === 500) {
                    $("#message").html("管理密码未设置，无法进入控制台！<br>请编辑文件 '" + response.data.data + "'<br>中 'password' 的值，然后点击\"重载\"按钮或重启服务端后刷新再试。<br>" +
                        "<button class=\"btn btn-success\" onclick=\"reloadServer()\" type=\"button\" style='margin-top: 16px'>重载</button>");
                    $("#message").fadeIn(1000);
                } else {
                    axios.get('/api/admin/check')
                        .then(function (response) {
                                if (response.data.code === 200) {
                                    showConfig();
                                } else {
                                    showLogin();
                                }
                            }
                        );
                }
            }
        );
});

function adminLogin() {
    var password = $("#admin-password").val();
    $("#admin-password").val("");
    var data = new FormData();
    data.append("password", password);
    axios.post('/api/admin/login', data)
        .then(function (response) {
                if (response.data.code === 200) {
                    $("#admin-password").css("border-color", "green");
                    $("#admin-button").css("border-color", "green");
                    $("#admin-icon").removeClass();
                    $("#admin-icon").addClass("glyphicon glyphicon-ok");
                    $("#admin-password").hide(500);
                    $("#admin-button").hide(500);
                    showConfig();
                } else {
                    $("#admin-password").css("border-color", "red");
                    $("#admin-button").css("border-color", "red");
                    $("#admin-icon").removeClass();
                    $("#admin-icon").addClass("glyphicon glyphicon-remove");
                }
            }
        );
}

function logout() {
    axios.get('/api/admin/logout')
        .then(function (response) {
                location.href = "/";
            }
        );
}

function showLogin() {
    $("#message").html("<div class='row'><div class='col-lg-12'><div class='input-group'><input id='admin-password' class='form-control' placeholder='请输入管理员密码' type='password'><span class='input-group-btn'><button id='admin-button' class='btn btn-default' type='button' onclick='adminLogin()'><i id='admin-icon' class='glyphicon glyphicon-record'></i></button></span></div></div></div>");
    $("#message").fadeIn(1000);
    $("#admin-password").keyup(function (event) {
        if (event.keyCode === 13) {
            adminLogin();
        }
    });
}