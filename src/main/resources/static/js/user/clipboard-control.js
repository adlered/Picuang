var clipboard = new ClipboardJS('.copies');

clipboard.on('success', function (e) {
    $("#clipboardStatus").html("<div class=\"alert alert-success\" style=\"text-shadow: none\" role=\"alert\">已复制至剪贴板。</div>");
    setTimeout(function () {
        $("#clipboardStatus").html("");
    }, 1500);
});

clipboard.on('error', function (e) {
    $("#clipboardStatus").html("<div class=\"alert alert-danger\" style=\"text-shadow: none\" role=\"alert\">抱歉，您的浏览器不支持！请手动复制。</div>");
    setTimeout(function () {
        $("#clipboardStatus").html("");
    }, 1500);
});