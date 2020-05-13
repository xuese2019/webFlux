var pageN = 0;
page(0,pageN);
//分页查询 json版
function page(pageSize,pageNow){
    pageN = pageNow;
    pageSize = pageSize <= 0 ? 8 : pageSize;
    $.ajax({
        url:root+"/api/user/json/"+pageSize+"/"+pageNow,
//        url:root+"/api/user/stream/"+pageSize+"/"+pageNow,
        headers:{'auth':localStorage.getItem("auth")},
        contentType: "application/json",
        data: JSON.stringify({"account":$("#table_search").val()}),
        type:"POST",
        dataType:"json",
        beforeSend:function(){
            //请求前的处理
            $("#table_search").addClass('disabled');
            $("#table-data").find("tr").remove();
        },
        success:function(req){
            //请求成功时处理
            $(req).each(function(i,e){
                $("#table-data").append(tr((i+1),e));
            });
        },
        complete:function(){
            //请求完成的处理
            $("#table_search").removeClass('disabled');
        },
        error:function(e){
            //请求出错处理
            console.log(e.responseText);
            alert2('error',e.responseText);
        }
    });
}
// form版
function page2(pageSize,pageNow){
    pageN = pageNow;
    pageSize = pageSize <= 0 ? 8 : pageSize;
    $.ajax({
        url:root+"/api/user/form/"+pageSize+"/"+pageNow,
//        url:root+"/api/user/stream/"+pageSize+"/"+pageNow,
        headers:{'auth':localStorage.getItem("auth")},
        data:$("#searchForm").serialize(),
//        data:{"account":"7"},
        type:"POST",
        dataType:"json",
        beforeSend:function(){
            //请求前的处理
            $("#table_search").addClass('disabled');
            $("#table-data").find("tr").remove();
        },
        success:function(req){
            //请求成功时处理
//            console.log(req);
            $(req).each(function(i,e){
                $("#table-data").append(tr((i+1),e));
            });
        },
        complete:function(){
            //请求完成的处理
            $("#table_search").removeClass('disabled');
        },
        error:function(e){
            //请求出错处理
            console.log(e.responseText);
            alert2('error',e.responseText);
        }
    });
}
//table tr模板
function tr(i,obj){
    return '<tr>'
            +'    <td>'
            +'        <font style="vertical-align: inherit;">'
            +'            <font style="vertical-align: inherit;">'+i+'</font>'
            +'        </font>'
            +'    </td>'
            +'    <td>'
            +'        <font style="vertical-align: inherit;">'
            +'            <font style="vertical-align: inherit;">'+obj.account+'</font>'
            +'        </font>'
            +'    </td>'
//            +'    <td>'
//            +'        <font style="vertical-align: inherit;">'
//            +'            <font style="vertical-align: inherit;">'+obj.systime+'</font>'
//            +'        </font>'
//            +'    </td>'
            +'    <td>'
//            +'        <button type="button" class="btn btn-warning btn-sm" onclick="restPwd(\''+obj.uuid+'\',this)">重置密码</button>'
            +'        <button type="button" class="btn btn-danger btn-sm" onclick="del(\''+obj.uuid+'\',this)">删除</button>'
            +'    </td>'
            +'</tr>';
}

//添加用户
function add(obj){
    $.ajax({
        url:root+"/api/user",
        headers:{'auth':localStorage.getItem("auth")},
        contentType: "application/json",
        data: JSON.stringify($("#addUser").serializeObject()),
//        data:$("#addUser").serialize(),
        dataType:"json",
        type:"POST",
        beforeSend:function(){
            //请求前的处理
            $(obj).addClass('disabled');
        },
        success:function(req){
            //请求成功时处理
            $("#addUser")[0].reset();
//            $('#modal-add-user').modal('hide');
            alert2('success','成功');
            page(0,pageN);
        },
        complete:function(){
            //请求完成的处理
            $(obj).removeClass('disabled');
        },
        error:function(e){
            //请求出错处理
            console.log(e.responseText);
            alert2('error',e.responseText);
        }
    });
}
//上传头像
function fileUp(obj){
    var formData = new FormData();
    formData.append("file",$('#file')[0].files[0]);
    $.ajax({
        url:root+"/api/file/upload",
        headers:{'auth':localStorage.getItem("auth")},
        dataType:"json",data:formData,
        type:"POST",
        processData: false,
        contentType: false,
        beforeSend:function(){
            //请求前的处理
            $(obj).addClass('disabled');
        },
        success:function(req){
            //请求成功时处理
            alert2('success',req);
        },
        complete:function(){
            //请求完成的处理
            $(obj).removeClass('disabled');
        },
        error:function(e){
            //请求出错处理
            alert2('error',e.responseText);
        }
    });
}
//删除用户
function del(e,obj){
    alert2Ok('warning','警告',"您的操作将会删除该信息！")
    .then(function(isConfirm){
        if(isConfirm.value === true){
            $.ajax({
                url:root+"/api/user/"+e,
                headers:{'auth':localStorage.getItem("auth")},
                dataType:"json",//        data:$("#addUser").serialize(),
                type:"DELETE",
                beforeSend:function(){
                    //请求前的处理
                    $(obj).addClass('disabled');
                },
                success:function(req){
                    //请求成功时处理
//                    无返回值所以走error
                },
                complete:function(){
                    //请求完成的处理
                    $(obj).removeClass('disabled');
                },
                error:function(e){
                    //请求出错处理
                     alert2('success','成功');
                     page(0,pageN);
                }
            });
        }
    });
}
//重置密码
function restPwd(e,obj){
    if(confirm("是否确定重置该密码？")){
        $.ajax({
            url:root+"/api/user/restPwd/"+e,
            headers:{'auth':localStorage.getItem("auth")},
            dataType:"json",//        data:$("#addUser").serialize(),
            type:"PUT",
            beforeSend:function(){
                //请求前的处理
                $(obj).addClass('disabled');
            },
            success:function(req){
                //请求成功时处理
                if(req.status){
                    alert2('success','成功');
                    page(0,pageN);
                }else{
                    alert2('error',req.msg);
                }
            },
            complete:function(){
                //请求完成的处理
                $(obj).removeClass('disabled');
            },
            error:function(){
                //请求出错处理
                console.log("error");
            }
        });
    }
}