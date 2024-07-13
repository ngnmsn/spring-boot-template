$(function() {

  // tooltipの初期化
  var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
  var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
    return new bootstrap.Tooltip(tooltipTriggerEl)
  })

  // IDフル桁コピー
  $('#copy').on('click',function(){
    const id = $(this).data('id');
    navigator.clipboard.writeText(id);
  });
});