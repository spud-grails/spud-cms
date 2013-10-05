spud.admin.date_picker = {};

(function() {
  var picker = spud.admin.date_picker;

  parsePickerDate = function(value) {
    var date = new Date();

    var currentDateTimeComponents = value.split(" ");
    var dateComponents = currentDateTimeComponents[0].split("-");
    if(dateComponents.length >= 3) {
      date = new Date(parseInt(dateComponents[0],10),parseInt(dateComponents[1],10) - 1,parseInt(dateComponents[2],10));
    }
    return date;
  }

  picker.init = function(selector) {
      selector = (typeof(selector) == 'undefined') ? '.spud_form_date_picker' : selector;
      $(selector).each(function() {
        var $this = $(this);
        var baseName = $this.attr('name');
        $this.attr('name',baseName.replace("[","_").replace("]",""));
        $this.datepicker({
          'format': 'yyyy-mm-dd',
          'autoclose': true
        });
        $this.val($this.val().split(" ")[0]);
        var date = parsePickerDate($this.val());

        var yearField = $("<input type='hidden'/>");
          yearField.attr('name',baseName.replace(']',"(1i)]"));
          yearField.val(date.getFullYear());
        var monthField = $("<input type='hidden'/>");
          monthField.attr('name',baseName.replace(']',"(2i)]"));
          monthField.val(date.getMonth() + 1);
        var dayField = $("<input type='hidden'/>");
          dayField.attr('name',baseName.replace(']',"(3i)]"));
          dayField.val(date.getUTCDate());
        yearField.insertAfter($this);
        monthField.insertAfter($this);
        dayField.insertAfter($this);


        $this.bind('change', function(evt) {
          var date = parsePickerDate($(this).val());
          yearField.val(date.getFullYear());
          monthField.val(date.getMonth() + 1);
          dayField.val(date.getUTCDate());

        });


      });

  };
})();
