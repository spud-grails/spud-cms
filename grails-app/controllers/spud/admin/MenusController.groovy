package spud.admin

import  spud.cms.*
import  spud.core.*

@SpudApp(name="Menus", thumbnail="spud/admin/menu_icon.png", order="2")
@SpudSecure(['MENUS'])
class MenusController {
    def spudPageService
    def spudMultiSiteService

    static namespace = "spud_admin"

    def index() {
		println "index params: ${params}"
        def menus = SpudMenu.createCriteria().list([sort: 'name', max: 25] + params) {
            eq('siteId',spudMultiSiteService.activeSite.siteId)
        }
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
        menu.siteId = spudMultiSiteService.activeSite.siteId

        if(menu.save(flush:true)) {
            spudPageService.evictCache()
            redirect resource: 'menus/menuItems', menusId: menu.id, action: 'index', namespace: 'spud_admin'
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
        menu.siteId = spudMultiSiteService.activeSite.siteId

        if(menu.save(flush:true)) {
            spudPageService.evictCache()
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
        spudPageService.evictCache()
        flash.notice = "Menu Removed Successfully!"
        redirect resource: 'menus', action: 'index', namespace: 'spud_admin'
    }

    private loadMenu() {
    if(!params.id) {
            flash.error = "Menu Id Not Specified"
            redirect controller: 'menus', action: 'index', namespace: 'spud_admin'
            return null
        }

        def menu = SpudMenu.findBySiteIdAndId(spudMultiSiteService.activeSite.siteId, params.long('id'))
        if(!menu) {
            flash.error = "Menu not found!"
            redirect controller: 'menus', action: 'index', namespace: 'spud_admin'
            return null
        }
        return menu
    }
}
