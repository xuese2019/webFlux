//跳转页面
function to(a,b){
    $.ajax({
        type:'get',
        url:root+'/page/'+a+'/'+b,
        dataType:'html',
        success:function(result){
            $("#content").html(result);
        }
    });
}
//注销登录
function logout(){
    localStorage.clear();
    location.replace(root+"/");
}