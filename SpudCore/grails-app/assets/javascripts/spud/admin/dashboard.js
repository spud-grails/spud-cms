Spud = (typeof(Spud) == 'undefined') ? {} : Spud;
Spud.Admin = (typeof(Spud.Admin) == 'undefined') ? {} : Spud.Admin;

Spud.Admin.Dashboard = new function() {
	var self=this;

	self.editMode = false;
	self.mouseIsDown = false;
	self.init = function() {
		// $('.admin_application').effect('shake',{},100,self.shakeLoop)
		$('.admin_application').bind('mousedown',self.mouseDown);
		$('.admin_application').bind('mouseup',self.mouseUp);

		$('.admin_application').bind('click',self.mouseClick);
		$('#dashboard-editsave').click(self.saveDashboard);
		$('#dashboard-editcancel').click(self.cancelDashboard);
		$('.sortable').sortable({disabled:true});
		// $('.admin_application').addClass('jiggly')
		// $('.admin_application').draggable();
	}
	self.mouseDown = function() {
		self.mouseIsDown = true;
		if(self.editMode == false)
		{
			setTimeout(function() {
				if(self.mouseIsDown)
				{
					self.enableEditMode();
				}
			},1000);
		}
	}

	self.mouseClick = function(evt) {
		if(self.editMode)
		{
			evt.stopPropagation();
			return false;
		}
	}
	self.mouseUp = function() {
		self.mouseIsDown = false;
	}
	self.enableEditMode = function() {
		self.editMode = true;
		$('.admin_application').addClass('jiggly');
		
		$('.sortable').sortable("enable");
		$('#dashboard-editmode').show();
	}
	self.saveDashboard = function() {
		self.saveOrder();
		self.disableEditMode();
	}
	self.cancelDashboard = function() {
		$('.sortable').sortable("cancel");
		self.disableEditMode();
	}
	self.disableEditMode = function() {
		self.editMode = false;
		$('#dashboard-editmode').hide();
		$('.admin_application').removeClass('jiggly');
		$('.sortable').sortable("disable");
	}

	
	self.saveOrder = function() {
		var appOrder = $('.sortable').sortable("toArray");
		for(var appCounter=0;appCounter < appOrder.length;appCounter++)
		{
			appOrder[appCounter] = appOrder[appCounter].replace('application_name_',"");
			
		}
		var keyvalue = appOrder.join(",")
		console.log(keyvalue);
		jQuery.ajax('/spud/save_key?key=app_order&value=' + keyvalue);
	}

	
}