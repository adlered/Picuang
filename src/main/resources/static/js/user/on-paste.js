document.getElementsByTagName('html')[0].addEventListener('paste', function (e) {
    if (!(e.clipboardData && e.clipboardData.items)) {
        return;
    }
    var file = null;
    var filename = null;
    for (var i = 0, len = e.clipboardData.items.length; i < len; i++) {
        var item = e.clipboardData.items[i];
        if (item.kind === "string") {
            item.getAsString(function (str) {
                filename = str;
            });
        } else if (item.kind === "file") {
            file = item.getAsFile();
        }
    }
    setTimeout(function () {
        console.log("file::" + file);
        console.log("name::" + filename);
        if (filename !== null) {
            if (!(filename.search("[^*|\\:\"<>?/]+\\.[^*|\\:\"<>?/\u4E00-\u9FA5]+"))) {
                // 有文件名
                console.log("有文件名");
                file = new File([file], filename, {type: file.type});
            } else {
                console.log("无文件名");
            }
        }
        console.log("最终文件名：" + file.name);
        uploadToServer(file);
    }, 400);
});