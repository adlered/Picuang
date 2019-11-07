$(function () {
    window.Notification.requestPermission(function (status) {});
});

function sendNotify(str) {
    if (window.Notification.permission === "granted") {
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
