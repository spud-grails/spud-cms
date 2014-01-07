//= require spud/admin/preinit
//= require jquery/jquery-1.9.1.min.js
//= require grails_ujs
//= require tiny_mce/jquery.tinymce.js
//= require tiny_mce/tiny_mce_src.js
//= require spud/admin/jquery.dataTables.min
//= require bootstrap/js/bootstrap
//= require datepicker/js/bootstrap-datepicker
//= require jquery-ui/js/jquery-ui-1.9.1.custom
//= require retina_tag
//= require_self
//= require_full_tree .

spud = {admin:{}};

$(document).ready(function() {

  if(typeof(window.console) == 'undefined'){
    window.console = {
      log:function(){},
      warn:function(){},
      error:function(){}
    };
  }

  spud.admin.editor.init();

    // $('#user_table').dataTable({
    //   "bJQueryUI": true,
    //   "sPaginationType": "full_numbers"
    // });

    $("#modal_window .modal-footer .form-submit").bind('click', function() {
      $("#modal_window .modal-body form").submit();
    });

    $("#modal_window ").on('hidden', function(){
      $(this).find('.modal-footer-additional').remove();
      $(this).find('.modal-footer-default').show();
      $(this).removeData('modal');
    });

    $('body').on('click', 'a.ajax', function() {
      var url = this.href;
      var title = this.title;
      var dialog = $("#modal_window");

      $("#modal_window .modal-title").text(title);
      dialog.modal({
        remote: url +".js",
        show:true
      });

      // dialog.load(url + ".js",
      // function(responseText, textStatus, XMLHttpRequest) {
      //   dialog.dialog({width:500,modal:true,height:500,title:title});
      // });
      return false;
    });


    $('body').on('ajax:success', 'a[data-method="delete"]',
      function(data, textStatus, jqXHR){
        $(this).closest('tr').fadeOut();
    });


      $('a.button').button();


      $('input[type=submit].btn').click(function() {$(this).button('loading');});

    $('#multisite_switcher select').change(function() {
      $(this).parent().submit();
    });

} );


function remove_fields(link) {
  $(link).prev("input[type=hidden]").val("1");
  $(link).closest(".fields").hide();
}

function add_fields(link, association, content) {
  var new_id = new Date().getTime();
  var regexp = new RegExp("new_" + association, "g")
  $(link).parent().before(content.replace(regexp, new_id));
}




function initTinyMCE(selector) {
  console.warn('Deprecation Notice: `initTinyMCE()` is deprecated, please use `spud.admin.editor.init()` instead.');
  spud.admin.editor.init();
}

function initDatePicker(selector){
  console.warn('Deprecation Notice: `initDatePicker()` is deprecated, please use `spud.admin.date_picker.init()` instead.');
  spud.admin.date_picker.init();

}

function displayModalDialogWithOptions(options){
  var modal = $('#modal_window');
  if(options.title){
    modal.find('.modal-title').text(options.title);
  }
  if(options.html){
    modal.find('.modal-body').html(options.html);
  }
  var defaultFooter = modal.find('.modal-footer-default');
  if(options.buttons){
    var newFooter = defaultFooter.clone();
    newFooter.addClass('modal-footer-additional');
    newFooter.find('.form-submit').remove();
    for(var key in options.buttons){
      newFooter.append('<button class="btn '+key+'">'+options.buttons[key]+'</button>');
    }
    defaultFooter.hide();
    modal.append(newFooter);
  }
  else{
    defaultFooter.show();
  }
  modal.modal('show');
}

function hideModalDialog(){
  var modal = $('#modal_window');
  modal.modal('hide');
}
