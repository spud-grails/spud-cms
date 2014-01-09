package spud.admin
import  spud.cms.*
import  spud.core.*

@SpudApp(name="Menus", thumbnail="spud/admin/menus_thumb.png")
@SpudSecure(['MENUS'])
class MenusController {
	static namespace = "spud_admin"

	def index() {
		def menus = SpudMenu.list([sort: 'name', max: 25] + params)
		render view: '/spud/admin/menus/index', model:[menus: menus, menuCount: SpudMenu.count()]
	}

	def create() {
		def menu = new SpudMenu()
  	render view: '/spud/admin/menus/create', model:[menu: menu]
	}

	def save() {
    if(!params.menu) {
      flash.error = "Menu submission not specified"
      redirect resource: 'menus', action: 'index', namespace: 'spud_admin'
      return
    }

    def menu = new SpudMenu(params.menu)



    if(menu.save(flush:true)) {
      redirect resource: 'menus', action: 'index', namespace: 'spud_admin'
    } else {
      flash.error = "Error Saving Menu"
      render view: '/spud/admin/menus/create', model:[menu:menu]
    }
	}

	def edit = {
		def menu = loadMenu()
		if(!menu) {
			return
		}
    render view: '/spud/admin/menus/edit', model: [menu: menu]

	}

	def update() {
		def menu = loadMenu()
		if(!menu) {
			return
		}
    menu.properties += params.menu

    if(menu.save(flush:true)) {
      redirect resource: 'menus', action: 'index', namespace: 'spud_admin'
    } else {
      render view: '/spud/admin/menus/edit', model: [menu: menu]
    }
	}

	def delete() {
		def menu = loadMenu()
		if(!menu) {
			return
		}
		menu.delete()
		flash.notice = "Menu Removed Successfully!"
    redirect resource: 'menus', action: 'index', namespace: 'spud_admin'
	}

	private loadMenu() {
  	if(!params.id) {
			flash.error = "Menu Id Not Specified"
			redirect controller: 'menus', action: 'index', namespace: 'spud_admin'
			return null
		}

		def menu = SpudMenu.get(params.id)
		if(!menu) {
			flash.error = "Menu not found!"
			redirect controller: 'menus', action: 'index', namespace: 'spud_admin'
			return null
		}
		return menu
	}
}
