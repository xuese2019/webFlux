page(0,1);
//分页查询
function page(pageSize,pageNow){
    pageSize = pageSize <= 0 ? 8 : pageSize;
    $.ajax({
        url:root+"/user/user/"+pageSize+"/"+pageNow,
        headers:{'auth':localStorage.getItem("auth")},
        async:true,
        data:{"account":$("#table_search").val()},
        type:"POST",
        dataType:"json",
        beforeSend:function(){
            //请求前的处理
//            $(obj).attr('disabled',true);
            $("#table-data").find("tr").remove();
        },
        success:function(req){
            //请求成功时处理
            console.log(req);
//            if(req.status){
//                $(req.data.records).each(function(i,e){
//                    $("#table-data").append(tr((i+1),e));
//                });
//                pageUtil(req.data);
//            }else{
//                alert(req.msg);
//            }
        },
        complete:function(){
            //请求完成的处理
//            $(obj).removeAttr('disabled');
        },
        error:function(e){
            //请求出错处理
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
            +'    <td>'
            +'        <font style="vertical-align: inherit;">'
            +'            <font style="vertical-align: inherit;">'+obj.systime+'</font>'
            +'        </font>'
            +'    </td>'
            +'    <td>'
            +'        <button type="button" class="btn btn-warning btn-sm" onclick="restPwd(\''+obj.uuid+'\',this)">重置密码</button>'
            +'        <button type="button" class="btn btn-danger btn-sm" onclick="del(\''+obj.uuid+'\',this)">删除</button>'
            +'    </td>'
            +'</tr>';
}

//添加用户
function add(obj){
    $.ajax({
        url:root+"/user/user",
        dataType:"json",
        async:true,
        data:$("#addUser").serialize(),
        type:"POST",
        beforeSend:function(){
            //请求前的处理
            $(obj).attr('disabled',true);
        },
        success:function(req){
            //请求成功时处理
            if(req.status){
                $("#addUser")[0].reset();
                $('#modal-add-user').modal('hide');
                $('.show').remove();
                alert2('success','成功');
                page(0,1);
            }else{
                alert2('error',req.msg);
            }
        },
        complete:function(){
            //请求完成的处理
            $(obj).removeAttr('disabled');
        },
        error:function(){
            //请求出错处理
            console.log("error");
        }
    });
}
//删除用户
function del(e,obj){
    if(confirm("是否确定删除该记录？")){
        $.ajax({
            url:root+"/user/user/"+e,
            dataType:"json",
            async:true,
    //        data:$("#addUser").serialize(),
            type:"DELETE",
            beforeSend:function(){
                //请求前的处理
                $(obj).attr('disabled',true);
            },
            success:function(req){
                //请求成功时处理
                if(req.status){
                    alert2('success','成功');
                    page(0,1);
                }else{
                    alert2('error',req.msg);
                }
            },
            complete:function(){
                //请求完成的处理
                $(obj).removeAttr('disabled');
            },
            error:function(){
                //请求出错处理
                console.log("error");
            }
        });
    }
}
//重置密码
function restPwd(e,obj){
    if(confirm("是否确定重置该密码？")){
        $.ajax({
            url:root+"/user/user/restPwd/"+e,
            dataType:"json",
            async:true,
    //        data:$("#addUser").serialize(),
            type:"PUT",
            beforeSend:function(){
                //请求前的处理
                $(obj).attr('disabled',true);
            },
            success:function(req){
                //请求成功时处理
                if(req.status){
                    alert2('success','成功');
                    page(0,1);
                }else{
                    alert2('error',req.msg);
                }
            },
            complete:function(){
                //请求完成的处理
                $(obj).removeAttr('disabled');
            },
            error:function(){
                //请求出错处理
                console.log("error");
            }
        });
    }
}