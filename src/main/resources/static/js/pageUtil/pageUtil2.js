function up(){
    $(".page-item").addClass('disabled');
    if(pageN > 0){
        pageN = pageN - 1;
        page(0,pageN);
    }
    $(".page-item").removeClass('disabled');
}
function dow(){
    let si = $("#table-data").find("tr").length;
    if(si > 0){
        $(".page-item").addClass('disabled');
        pageN = pageN + 1;
        page(0,pageN);
        $(".page-item").removeClass('disabled');
    }else{
        $(".page-item-dow").addClass('disabled',true);
    }
}