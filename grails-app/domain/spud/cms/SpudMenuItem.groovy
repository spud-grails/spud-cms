package spud.cms
import grails.util.GrailsNameUtils


class SpudMenuItem {
	static transients = ['menuItems','parent', 'urlName']
	static belongsTo = [menu: SpudMenu]
	String name
	String parentType
	Long   parentId
	String classes
	Integer menuOrder = 0
	String url
	SpudPage page

	Date dateCreated
	Date lastUpdated

	static mapping = {
		def cfg = it?.getBean('grailsApplication')?.config
		datasource(cfg?.spud?.core?.datasource ?: 'DEFAULT')
		table 'spud_menu_items'
		autoTimestamp true

		menu column: 'spud_menu_id'
		page column: 'spud_page_id'

		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'

		parentType index: 'idx_menu_item_parent'
		parentId   index: 'idx_menu_item_parent_id,idx_menu_item_parent'
	}

  static constraints = {
  	name       blank:false
  	menu       nullable: false
  	parentType nullable: false
  	parentId   nullable: false
  	url        nullable: true
  	page       nullable: true
  	menuOrder  nullable: true
  	classes    nullable: true
  }


  def beforeDelete() {
		SpudMenuItem.withNewSession { session ->
			def menuItems = SpudMenuItem.read(this.id).menuItems
			menuItems*.delete(flush:true)
		}
	}

  Set<SpudMenuItem> getMenuItems() {
  	if(id) {
  		return SpudMenuItem.withCriteria(readOnly:true) {
  			eq('parentType','SpudMenuItem')
  			eq('parentId', id)
  			order('menuOrder')
  		} as Set

  	}
  	return null
  }

  String getUrlName() {
    if(this.page) {
      return page.urlName
    } else {
      return null
    }
  }

  public setParent(parent) {
  	if(parent) {
  		parentType =  GrailsNameUtils.getShortName(parent.class)
	  	parentId   =  parent.id
  	}
  	else {
  		parentType = null
  		parentId = null
  	}
  }

  def getParent() {
    if(parentType == 'SpudMenuItem') {
      return SpudMenuItem.get(parentId)
    } else {
      return SpudMenu.get(parentId)
    }
  }

  static grouped(menu) {
  	menu.menuItemsCombined.groupBy { it.parentType }
  }

  def grailsCacheAdminService
  def afterInsert() {
    grailsCacheAdminService.clearAllCaches()
  }

  def afterUpdate() {
    grailsCacheAdminService.clearAllCaches()
  }

  def afterDelete() {
    grailsCacheAdminService.clearAllCaches()
  }
}
