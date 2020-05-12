function sub(){
    $.ajax({
        url:root+"/login/login",
        dataType:"json",data:$("#login").serialize(),
        type:"POST",
        beforeSend:function(){
            $('#submit').attr('disabled',true);
        },
        success:function(req){
            localStorage.setItem("auth",req.password);
//            window.location.href=root+"/page/home/home";
            location.replace(root+"/page/home/home");
        },
        complete:function(){
            $('#submit').removeAttr('disabled');
        },
        error:function(e){
            alert(e.responseText);
        }
    });
}