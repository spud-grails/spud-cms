package spud.admin

import  spud.cms.*
import  spud.core.*
import groovy.json.*

@SpudApp(name="Menus", subsection="Menu Items", thumbnail="spud/admin/menu_icon.png")
@SpudSecure(['MENUS'])
class MenuItemsController {
	def spudMultiSiteService
	static namespace = "spud_admin"

	def index() {
		def menu = loadMenu()
		if(!menu) {
			return
		}

		def menuItems = SpudMenuItem.withCriteria(readOnly:true) {
			eq('menu',menu)
			eq('parentType','SpudMenu')
			order('menuOrder')
		}

		render view: '/spud/admin/menu_items/index', model:[menuItems: menuItems, menu: menu]
	}

	def create() {
		def menu = loadMenu()
		if(!menu) {
			return
		}
		def menuItem = new SpudMenuItem(menu: menu)
		render view: '/spud/admin/menu_items/create', model:[menuItem: menuItem, menu: menu]
	}

	def save() {
		def menu = loadMenu()
		if(!menu) {
			return
		}
		if(!params.menuItem) {
			flash.error = "Menu Item submission not specified"
			redirect resource: 'menus/menuItems', action: 'index', menusId: menu.id, namespace: 'spud_admin'
			return
		}
		def menuItemParams = params.menuItem.clone()
		menuItemParams.remove('parentId')
		def menuItem = new SpudMenuItem(menuItemParams)
		menuItem.menu = menu
		if(!params.menuItem.parentId) {
			menuItem.parent = menu
			} else {
				def parentMenuItem = SpudMenuItem.findByMenuAndId(menu,params.menuItem.parentId)
				if(parentMenuItem) {
					menuItem.parent = parentMenuItem
				}
			}

			def highestSiblings = menuItem.parent.menuItems.sort{ -it.menuOrder }
			if(highestSiblings && highestSiblings.size() > 0) {
				menuItem.menuOrder = highestSiblings[0].menuOrder + 1
			}


			if(menuItem.save(flush:true)) {
				redirect resource: 'menus/menuItems', action: 'index', menusId: menu.id, namespace: 'spud_admin'
				} else {
					flash.error = "Error Saving Menu Item"
					render view: '/spud/admin/menu_items/create', model:[menuItem:menuItem, menu: menu]
				}
			}

			def edit = {
				def menu = loadMenu()
				if(!menu) {
					return
				}
				def menuItem = loadMenuItem(menu)
				if(!menuItem) {
					return
				}
				render view: '/spud/admin/menu_items/edit', model: [menu: menu, menuItem: menuItem, menu: menu]

			}

			def update() {
				def menu = loadMenu()
				if(!menu) {
					return
				}
				def menuItem = loadMenuItem(menu)
				if(!menuItem) {
					return
				}

				def menuItemParams = params.menuItem.clone()

				menuItem.properties += menuItemParams
				if(!params.menuItem.parentId) {
					menuItem.parent = menu
				} else {
					def parentMenuItem = SpudMenuItem.findByMenuAndId(menu,params.menuItem.parentId)
					if(parentMenuItem) {
						menuItem.parent = parentMenuItem
					}
				}

				if(menuItem.save(flush:true)) {
					redirect resource: 'menus/menuItems', action: 'index', menusId: menu.id, namespace: 'spud_admin'
				} else {
					render view: '/spud/admin/menu_items/edit', model: [menu: menu, menuItem: menuItem, menu: menu]
				}
			}

			def delete() {
				def menu = loadMenu()
				if(!menu) {
					return
				}
				def menuItem = loadMenuItem(menu)
				if(!menuItem) {
					return
				}
				menu.removeFromMenuItemsCombined(menuItem)
				menuItem.delete(flush:true)
				flash.notice = "Menu Item Removed Successfully!"
				redirect resource: 'menus/menuItems', action: 'index', menusId: menu.id, namespace: 'spud_admin'
			}

			def sort() {
				def menu = loadMenu()
				if(!menu) {
					return
				}
				def menuOrders = new JsonSlurper().parseText(params.menu_order)

				sortMenuItemsToParent(menu, menuOrders)
				render text:'', status:200
			}

			private sortMenuItemsToParent(parent,menuOrders) {
				def sortPosition = 0
				menuOrders.each { menuMeta ->
					def menuItem = SpudMenuItem.read(menuMeta.id)
					if(menuItem) {
						menuItem.menuOrder = sortPosition
						menuItem.parent = parent
						menuItem.save(flush:true)
						if(menuMeta.order != null) {
							sortMenuItemsToParent(menuItem, menuMeta.order)
						}
						sortPosition += 1
					}
				}
			}

			private loadMenuItem(menu) {
				if(!params.id) {
					flash.error = "Menu Item Id Not Specified"
					redirect controller: 'menus', action: 'index', namespace: 'spud_admin'
					return null
				}

				def menuItem = SpudMenuItem.findByMenuAndId(menu,params.id)
				if(!menuItem) {
					flash.error = "Menu Item not found"
					redirect controller: 'menus', action: 'index', namespace: 'spud_admin'
					return null
				}
				return menuItem
			}

			private loadMenu() {
				if(!params.menusId) {
					flash.error = "Menu Id Not Specified For Listing Menu Items"
					redirect controller: 'menus', action: 'index', namespace: 'spud_admin'
					return null
				}

				def menu = SpudMenu.get(params.menusId)
				if(!menu) {
					flash.error = "Menu not found for listing Menu Items!"
					redirect controller: 'menus', action: 'index', namespace: 'spud_admin'
					return null
				} else if(menu.siteId != spudMultiSiteService.activeSite.siteId) {
					flash.notice = "Site Context Switched!"
					redirect controller: 'menus', action: 'index', namespace: 'spud_admin'
				}

				return menu
			}
		}
