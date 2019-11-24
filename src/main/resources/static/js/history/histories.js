$(function () {
    var i = 0;
    axios.get('/api/year')
        .then(function (yearRes) {
                $.each(yearRes.data, function (key, data) {
                    var year = data;
                    $("#histories").append("" +
                        "<span id='" + year + "'></span>");
                    axios.get('/api/month?year=' + year)
                        .then(function (monthRes) {
                            $.each(monthRes.data, function (key, data) {
                                var month = data;
                                $("#" + year).append("" +
                                    "<span id='" + year + "-" + month + "'></span>");
                                $("#" + year + "-" + month).append("<span id='" + year + "-" + month + "-h" + "'><h3>" + year + " 年 " + month + " 月</h3></span>");
                                $("#" + year + "-" + month).append("<span id='" + year + "-" + month + "-p" + "'></span>");
                                axios.get('/api/day?year=' + year + '&month=' + month)
                                    .then(function (dayRes) {
                                        $.each(dayRes.data, function (key, data) {
                                            var day = data;
                                            $("#" + year + "-" + month + "-p").append("<span id='" + year + "-" + month + "-" + day  + "'><h4>" + day + "日</h4></span>");
                                            axios.get('/api/list?year=' + year + '&month=' + month + '&day=' + day)
                                                .then(function (listRes) {
                                                    $.each(listRes.data, function (key, data) {
                                                        var list = data;
                                                        $("#" + year + "-" + month + "-" + day).append("<a href='" + data.path + "' target='_blank'><img src='" + list.path + "' style='width: auto; height: auto; max-width: 128px; max-height: 128px; margin: 8px 4px' class='img-thumbnail'></a>");
                                                        if (i == 0) {
                                                            $("#histories").prepend("" +
                                                                "<h5>历史记录根据您的IP地址（" + data.ip + "）所生成，请及时保存，IP地址更改后历史记录将丢失。</h5>");
                                                        }
                                                        ++i;
                                                        $("#picCount").text(i);
                                                    });
                                                });
                                        });
                                    });
                            });
                        });
                });
            }
        );
});