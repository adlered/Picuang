var adminOnly;

$(function () {
    $("[data-toggle='popover']").popover();
});

function showConfig() {
    setTimeout(function () {
        // 一列
        axios.get('/api/admin/getConf?conf=imageUploadedCount')
            .then(function (response) {
                $("#config-row").prepend("<div class='col-lg-6'>" +
                    "<div class='input-group'><span class='input-group-addon' style='text-shadow: none !important;'><span class=\"glyphicon glyphicon-globe\"></span>" +
                    " 存储图片数量</span><input id='imageUploadedCount-input' aria-label='...' class='form-control' type='text' value='" + response.data + "'><span class='input-group-btn'>" +
                    "<button type=\"button\" class=\"btn btn-primary\" aria-label=\"Help\" onclick='getHelp(\"imageUploadedCount\")'><span class=\"glyphicon glyphicon-question-sign\"></span></button>" +
                    "<button class='btn btn-primary' type='button' onclick='editConfig(\"imageUploadedCount\")'>修改</button></span></div></div>");
            });
        axios.get('/api/admin/getConf?conf=password')
            .then(function (response) {
                $("#config-row").prepend("<div class='col-lg-6'>" +
                    "<div class='input-group'><span class='input-group-addon' style='text-shadow: none !important;'><span class=\"glyphicon glyphicon-user\"></span>" +
                    " 后台管理密码</span><input id='password-input' aria-label='...' class='form-control' type='text' value='" + response.data + "'><span class='input-group-btn'>" +
                    "<button type=\"button\" class=\"btn btn-primary disabled\" aria-label=\"Help\" onclick='getHelp(\"password\")'><span class=\"glyphicon glyphicon-question-sign\"></span></button>" +
                    "<button class='btn btn-primary' type='button' onclick='editConfig(\"password\")'>修改</button></span></div></div>");
            });
        axios.get('/api/admin/getConf?conf=cloneLimit')
            .then(function (response) {
                $("#config-row").prepend("<div class='col-lg-6'>" +
                    "<div class='input-group'><span class='input-group-addon' style='text-shadow: none !important;'><span class=\"glyphicon glyphicon-cutlery\"></span>" +
                    " 克隆频率限制</span><input id='cloneLimit-input' aria-label='...' class='form-control' type='text' value='" + response.data + "'><span class='input-group-btn'>" +
                    "<button type=\"button\" class=\"btn btn-primary\" aria-label=\"Help\" onclick='getHelp(\"cloneLimit\")'><span class=\"glyphicon glyphicon-question-sign\"></span></button>" +
                    "<button class='btn btn-primary' type='button' onclick='editConfig(\"cloneLimit\")'>修改</button></span></div></div>");
            });
        axios.get('/api/admin/getConf?conf=uploadLimit')
            .then(function (response) {
                $("#config-row").prepend("<div class='col-lg-6'>" +
                    "<div class='input-group'><span class='input-group-addon' style='text-shadow: none !important;'><span class=\"glyphicon glyphicon-open\"></span>" +
                    " 上传频率限制</span><input id='uploadLimit-input' aria-label='...' class='form-control' type='text' value='" + response.data + "'><span class='input-group-btn'>" +
                    "<button type=\"button\" class=\"btn btn-primary\" aria-label=\"Help\" onclick='getHelp(\"uploadLimit\")'><span class=\"glyphicon glyphicon-question-sign\"></span></button>" +
                    "<button class='btn btn-primary' type='button' onclick='editConfig(\"uploadLimit\")'>修改</button></span></div></div>");
            });
        // 二列
        axios.get('/api/admin/getConf?conf=adminOnly')
            .then(function (response) {
                if (response.data === "on") {
                    adminOnly = "启用";
                } else if (response.data === "off") {
                    adminOnly = "停用";
                } else {
                    adminOnly = "未知";
                }
                $("#adminOnlyStatus").html(adminOnly);
            });
        $("#logout").show(200);
        $("#config").show(500);
    }, 250);
}

function getHelp(key) {
    if (key === "imageUploadedCount") {
        tip("存储图片数量是在主页展示的\"为用户累计永久存储图\"的数值。<br>" +
            "修改本值不会影响图片的存储，用户每上传一张图片该数值就会+1。");
    } else if (key === "uploadLimit") {
        tip("设置上传的频率限制。<br>" +
            "冒号左侧代表\"时间\"，右侧代表\"次数\"。<br>" +
            "例如\"3:1\"代表\"每三秒允许上传一张图片\"。<br>" +
            "设置过小的数值会致使上传速度变慢。<br>" +
            "上传多张图片时达到限制会自动阻塞，不会影响正常上传。");
    } else if (key === "cloneLimit") {
        tip("设置克隆的频率限制。<br>" +
            "冒号左侧代表\"时间\"，右侧代表\"次数\"。<br>" +
            "例如\"3:1\"代表\"每三秒允许克隆一张图片\"。<br>" +
            "设置过小的数值会致使克隆速度变慢。<br>");
    }
}

function tip(text) {
    $("#helpText").html(text);
    $('#helpModal').modal();
}

function adminOnlyToggle() {
    if (adminOnly === "启用") {
        axios.get('/api/admin/setConf?conf=adminOnly&value=off')
            .then(function (response) {
                    adminOnly = "停用";
                    $("#adminOnlyStatus").html(adminOnly);
                    sendNotify("已停用：仅管理员上传模式。");
                }
            );
    } else if (adminOnly === "停用") {
        axios.get('/api/admin/setConf?conf=adminOnly&value=on')
            .then(function (response) {
                    adminOnly = "启用";
                    $("#adminOnlyStatus").html(adminOnly);
                    sendNotify("已启用：仅管理员上传模式。");
                }
            );
    } else {
        sendNotify("无法读取管理员上传模式。请检查配置文件是否有adminOnly项，且值为on或off，修改后点击下方\"重载\"按钮后重试。");
    }
}

function editConfig(sheet) {
    var value = $("#" + sheet + "-input").val();
    axios.get('/api/admin/setConf?conf=' + sheet + '&value=' + value)
        .then(function (response) {
                sendInnerNotify(sheet + " 的值已成功修改为：" + value);
            }
        );
}

function exportConfig() {
    window.open("/api/admin/export");
}

function importConfig() {
    var file = document.getElementById("import").files[0];
    uploadConfigToServer(file);
}

function uploadConfigToServer(file) {
    var param = new FormData();
    param.append('file', file);
    var config = {
        headers: {'Content-Type': 'multipart/form-data'}
    };
    axios.post('/api/admin/import', param, config)
        .then(function (response) {
                if (response.data.code === 200) {
                    sendNotify("配置导入成功！配置现已重载并生效。请刷新页面！");
                } else {
                    sendNotify("配置导入失败！请检查你的配置文件后缀名是否是.ini，且确认其可用性。")
                }
            }
        );
}

function reloadServer() {
    axios.get('/api/admin/reload')
        .then(function (response) {
                sendNotify("配置已重载！请刷新页面。");
            }
        );
}

verifyReNew = 0;
function reNewConfig() {
    if (verifyReNew === 0) {
        tip("您确定要生成新的配置文件吗？在生成新的配置文件之前，你应该将旧的配置文件导出并备份！<br>关闭本窗口后，再点一次\"重新生成配置文件\"按钮确定生成。");
        verifyReNew = 1;
    } else {
        axios.get('/api/admin/renew')
            .then(function (response) {
                    sendNotify("已重新生成配置文件！请刷新页面。");
                }
            );
        verifyReNew = 0;
    }
}