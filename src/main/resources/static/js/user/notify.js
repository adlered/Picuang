$(function () {
    window.Notification.requestPermission(function (status) {});
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