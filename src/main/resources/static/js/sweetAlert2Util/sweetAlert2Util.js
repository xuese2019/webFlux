var Toast = this.swal.mixin({
    toast: true,
    position: 'top-end',
    showConfirmButton: false,
    timer: 3000
});
/**
* ico: success info error warning
*/
function alert2(ico,title){
    Toast.fire({
        icon: ico,
        title: title
    })
}

var Toast2 = this.swal.mixin({
    toast: true,
    position: 'top',
    showConfirmButton: false
});
function alert2Ok(ico,title,text){
     return Toast2.fire({
        icon: ico,
        title: title,
        text: text,
        showConfirmButton: true,
        confirmButtonText: '确定',
        showCancelButton: true,
        cancelButtonText: '取消'
    });
}