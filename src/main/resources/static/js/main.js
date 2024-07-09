$(function() {
  $('#copy').on('click',function(){
    const id = $(this).data('id');
    navigator.clipboard.writeText(id);
    console.log("copyしました。");
  });
});