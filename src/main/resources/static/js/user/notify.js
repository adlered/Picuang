$(function () {
    window.Notification.requestPermission(function (status) {});

    axios.get('/api/admin/getConf?conf=adminOnly')
        .then(function (response) {
            if (response.data === "on") {
                axios.get('/api/admin/check')
                    .then(function (response) {
                            if (response.data.code === 200) {
                                $("#functions").show();
                            } else {
                                $("#functions").after("<div class='alert alert-danger' style='text-shadow: none; margin: 0px 0px 0px'>抱歉！根据管理员的设置，非管理员用户无法上传图片。</div>");
                            }
                        }
                    );
            } else if (response.data === "off") {
                $("#functions").show();
            }
        });
});

function sendNotify(str) {
    if (window.Notification.permission === "granted") {
        // Chrome提醒
        var title = "Picuang图床 - 提醒";
        var option = {
            body: str,
            icon: "/favicon.png"
        };
        var notify = new Notification(title, option);
        notify.onclick = function () {
            notify.close();
        };
    }
    sendInnerNotify(str);
}

function sendInnerNotify(str) {
    // 页内提醒
    var config = {
        title: "Picuang图床 - 提醒",
        body: str,
        inner: true,
        icon: "favicon.png",
        onclick: function (data) {}
    };
    new dToast(config);
}

function sendStatus(str) {
    $("#status").html(str);
}