function sub(){
    $.ajax({
        url:root+"/login/login",
        dataType:"text",
        contentType: "application/json",
        data: JSON.stringify($("#login").serializeObject()),
        type:"POST",
        beforeSend:function(){
            $('#submit').attr('disabled',true);
        },
        success:function(req){
            localStorage.setItem("auth",req);
//            window.location.href=root+"/page/home/home";
            location.replace(root+"/page/home/home");
        },
        complete:function(){
            $('#submit').removeAttr('disabled');
        },
        error:function(e){
            console.log(e.responseText);
            alert(e.responseText);
        }
    });
}