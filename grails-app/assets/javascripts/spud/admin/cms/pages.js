spud = (typeof(spud) == 'undefined') ? {} : spud;
spud.admin = (typeof(spud.admin) == 'undefined') ? {} : spud.admin;
spud.admin.cms = (typeof(spud.admin.cms) == 'undefined') ? {} : spud.admin.cms;
spud.admin.cms.pages = spud.admin.cms.pages || {};

(function(){
  var pages = spud.admin.cms.pages;


  pages.initFormTabs = function(){
    console.log("spud-cms initFormTabs called");
    var tabNames = [];

    $('.formtabs .formtab').each(function(tabind) {
      if(tabind === 0) {
        $(this).addClass('active');
      }
      this.id = 'tab-' + tabind;
      tabNames.push($('.tab_name',this).first().val());
    });
    console.log(tabNames)
    var tabButtons = $('.formtabs .formtab_buttons').first();
    for(var x=0;x<tabNames.length;x++)
    {
      var tabButton = $('<li><a href="#tab-' + x + '" data-toggle="tab">' + tabNames[x] + '</a></li>');
      if(x === 0) {
        tabButton.addClass('active');
      }
      tabButtons.append(tabButton);
    }

  };
})();
