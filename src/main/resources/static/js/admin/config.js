function showConfig() {
    axios.get('/api/admin/getConf?conf=imageUploadedCount')
        .then(function (response) {
            $("#config-row").prepend("<div class='col-lg-6'><div class='input-group'><span class='input-group-addon' style='text-shadow: none !important;'>累计存储图片数</span><input id='imageUploadedCount-input' aria-label='...' class='form-control' type='text' value='" + response.data + "'><span class='input-group-btn'><button class='btn btn-primary' type='button' onclick='editConfig(\"imageUploadedCount\")'>修改</button></span></div></div>");
        });
    axios.get('/api/admin/getConf?conf=password')
        .then(function (response) {
            $("#config-row").prepend("<div class='col-lg-6'><div class='input-group'><span class='input-group-addon' style='text-shadow: none !important;'>修改管理员密码</span><input id='password-input' aria-label='...' class='form-control' type='text' value='" + response.data + "'><span class='input-group-btn'><button class='btn btn-primary' type='button' onclick='editConfig(\"password\")'>修改</button></span></div></div>");
        });
    $("#logout").fadeIn(200);
    $("#config").fadeIn(100);
}

function editConfig(sheet) {
    var value = $("#" + sheet + "-input").val();
    axios.get('/api/admin/setConf?conf=' + sheet + '&value=' + value);
    sendInnerNotify(sheet + " 的值已成功修改为：" + value);
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
                alert(response.data.code);
            }
        );
}