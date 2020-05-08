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