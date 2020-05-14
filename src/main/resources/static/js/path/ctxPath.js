//表单序列化
$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if(o[this.name]) {
			if(!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};
//项目根
var root = window.location.protocol + '//' + window.location.host;
//ajax 全局设置
$.ajaxSetup({
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