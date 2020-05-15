//ajax 全局设置
$.ajaxSetup({
    headers:{'auth':localStorage.getItem("auth")},
    beforeSend: function (xmlHttp) {
        //ajax请求之前
        $("#load-bar").css("width","90%");
        $("#content-wrapper").append(
            "<div class=\"overlay\">"+
            "   <i class=\"fa fa-refresh fa-spin\"></i>"+
            "</div>"
        );
    },
    complete: function () {
        //ajax请求完成，不管成功失败
        $("#load-bar").css("width","100%");
        setTimeout(function(){
            $("#load-bar").css("width","0");
        },2000);
        $(".overlay").remove();
    },
    error: function (e) {
        //ajax请求失败
        console.log("error:"+e.responseText);
    }
});