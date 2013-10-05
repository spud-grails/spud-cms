//= require codemirror
//= require codemirror/modes/markdown

spud.admin.editor = {};

(function(){

  var editor = spud.admin.editor;

  var registeredPlugins = [
    'autolink','lists','layer','table','tableDropdown','advhr','advimage','advlink','iespell','inlinepopups','media','paste'
  ];

  var registeredButtons = [
    ['bold','italic','underline','strikethrough','|','justifyleft','justifycenter','justifyright','justifyfull','|','formatselect','cut','copy','paste','pastetext','|','bullist','numlist','outdent','indent','|','tableDropdown','|','link','unlink','anchor','image','code'],
    [],
    [],
    []
  ];

  var validElements = "@[id|class|style|title|dir<ltr?rtl|lang|xml::lang|onclick|ondblclick|" +
    "onmousedown|onmouseup|onmouseover|onmousemove|onmouseout|onkeypress|" +
    "onkeydown|onkeyup],a[rel|rev|charset|hreflang|tabindex|accesskey|type|" +
    "name|href|target|title|class|onfocus|onblur],strong/b,em/i,strike,u," +
    "#p,-ol[type|compact],-ul[type|compact],-li,br,img[longdesc|usemap|" +
    "src|border|alt=|title|hspace|vspace|width|height|align|hidpi_src],-sub,-sup," +
    "-blockquote,-table[border=0|cellspacing|cellpadding|width|frame|rules|" +
    "height|align|summary|bgcolor|background|bordercolor],-tr[rowspan|width|" +
    "height|align|valign|bgcolor|background|bordercolor],tbody,thead,tfoot," +
    "#td[colspan|rowspan|width|height|align|valign|bgcolor|background|bordercolor" +
    "|scope],#th[colspan|rowspan|width|height|align|valign|scope],caption,-div," +
    "-span,-code,-pre,address,-h1,-h2,-h3,-h4,-h5,-h6,hr[size|noshade],-font[face" +
    "|size|color],dd,dl,dt,cite,abbr,acronym,del[datetime|cite],ins[datetime|cite]," +
    "object[classid|width|height|codebase|*],param[name|value|_value],embed[type|width" +
    "|height|src|*],script[src|type],map[name],area[shape|coords|href|alt|target],bdo," +
    "button,col[align|char|charoff|span|valign|width],colgroup[align|char|charoff|span|" +
    "valign|width],dfn,fieldset,form[action|accept|accept-charset|enctype|method]," +
    "input[accept|alt|checked|disabled|maxlength|name|readonly|size|src|type|value]," +
    "kbd,label[for],legend,noscript,optgroup[label|disabled],option[disabled|label|selected|value]," +
    "q[cite],samp,select[disabled|multiple|name|size],small," +
    "textarea[cols|rows|disabled|name|readonly],tt,var,big";

  var extendedValidElements = [];

  var validFormats = "p,h1,h2,h3,h4,h5,h6";


  editor.init = function(options) {
    editor.monitorFormatters();
    options = options || {};
    var selector = options.selector || 'textarea.tinymce';
    $(selector).each(function() {
      var $this = $(this);
      var dataFormat = $this.attr('data-format');
      switch(dataFormat) {
        case 'Markdown':
          editor.initCodeMirrorWithOptions(this,'markdown', options || {});
          break;
        case 'HTML':
        default:
          editor.initMCEWithOptions(this, options || {});
      };


    });

  };

  editor.monitorFormatters = function() {
    $('select[data-formatter]').off('onchange');
    $('select[data-formatter]').change(editor.formatterChanged);
  };

  editor.formatterChanged = function() {
    var formatId = $(this).attr('data-formatter');
    editor.unload('#' + formatId);
    $('#' + formatId).attr('data-format',$(this).val());
    editor.init({selector: '#' + formatId});
  };

  editor.initMCEWithOptions = function(element, options){

    var theme = options.theme || 'advanced';
    var height = options.height || 400;

    $(element).tinymce({
    theme: theme,
    plugins: registeredPlugins.join(','),
    theme_advanced_toolbar_location: "top",
    theme_advanced_buttons1: registeredButtons[0].join(','),
    theme_advanced_buttons2: registeredButtons[1].join(','),
    theme_advanced_buttons3: registeredButtons[2].join(','),
    theme_advanced_buttons4: registeredButtons[3].join(','),
    theme_advanced_toolbar_align: 'left',
    theme_advanced_blockformats: validFormats,
    convert_urls: false,
    valid_elements: validElements,
    media_strict: false,
    extended_valid_elements: extendedValidElements.join(","),
    width: $(element).width(),
    height: height
    });


  };

  var codeMirrors = [];
  editor.initCodeMirrorWithOptions = function(element, format, options) {
    var editor = CodeMirror.fromTextArea(element, {
        mode: 'markdown',
        lineNumbers: true,
        lineWrapping: true,
        theme: "default",
        extraKeys: {"Enter": "newlineAndIndentContinueMarkdownList"}
      });
    codeMirrors.push(editor);

    $(element).attr('code-mirror-id',codeMirrors.length-1);
  };

  editor.unload = function(selectorOptional) {
    var selector = selectorOptional || 'textarea.tinymce';
    $(selector).each(function() {
      var $this = $(this);
      if($this.attr('code-mirror-id')) {
        codeMirrors[parseInt($this.attr('code-mirror-id'),10)].toTextArea();
        codeMirrors[parseInt($this.attr('code-mirror-id'),10)] = null;
        $this.removeAttr('code-mirror-id');
      }
        tinyMCE.execCommand('mceRemoveControl',false,this.id);
    });

  };



  editor.registerPlugin = function(pluginName){
    if($.inArray(registeredPlugins, pluginName) < 0){
      registeredPlugins.push(pluginName);
    }
  };

  editor.deregisterPlugin = function(pluginName){
    var i=0;
    while(i < registeredPlugins.length){
      if(registeredPlugins[i] == pluginName){
        registeredPlugins.splice(1, i);
        break;
      }
      i++;
    }
  };

  editor.appendValidElement = function(element) {
    extendedValidElements.push(element);
  };

  editor.registerButton = function(buttonNameOrArray, rowNum){
    rowNum = rowNum || 0;
    if(typeof(buttonNameOrArray) == 'object'){
      registeredButtons[rowNum].concat(buttonNameOrArray);
    }
    else{
      registeredButtons[rowNum].push(buttonNameOrArray);
    }
  };
})();
