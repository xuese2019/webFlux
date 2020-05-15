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
//页面添加auth头
//$("meta[name=\"auth\"]").val(localStorage.getItem("auth"));
