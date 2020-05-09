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
    let loc = localStorage;
    let lo = location;
    alert2Ok('warning','警告',"您的操作将会注销登录！")
    .then(function(isConfirm){
        if(isConfirm.value === true){
            loc.clear();
            lo.replace(root+"/");
        }
    });
}