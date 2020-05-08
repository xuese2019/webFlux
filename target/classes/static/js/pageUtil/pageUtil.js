//分页模板
function pageUtil(obj){
    $("#total").text(obj.total);
    $("#pages").text(obj.pages);
    $("#pages2").find("li").remove();
    for(let i = 1;i <= obj.pages;i++){
        $("#pages2").append('<li class="page-item '+ (obj.current === i ? 'active' : '')+'">'+
                            '     <a class="page-link" href="javascript:page(0,'+i+');">'+
                            '         <font style="vertical-align: inherit;">'+
                            '             <font style="vertical-align: inherit;">'+i+'</font>'+
                            '         </font>'+
                            '     </a>'+
                            '</li>');
    }
}