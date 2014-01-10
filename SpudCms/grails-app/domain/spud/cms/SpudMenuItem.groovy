package spud.cms
import grails.util.GrailsNameUtils


class SpudMenuItem {
	static transients = ['menuItems','parent']
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
  	parentType blank: false
  	parentId   blank: false
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


  // Returns an array of menu items in order of heirarchy
	// :filter Filters out a menu item by ID, and all of its children
	// :value Pick an attribute to be used in the value field, defaults to ID
  static optionsTreeForItem(menu, config=[:]) {
  	def collection = config.collection ?: SpudMenuItem.grouped(menu)
  	println collection
  	def level      = config.level ?: 1
  	def parentId   = config.parentId
  	def parentType = config.parentType ?: 'SpudMenu'
  	def filter     = config.filter
  	def value      = config.value ?: "id"
  	def list       = []
  	def itemCollection = collection
  	def recursiveCollectionClosure = { c ->
			if(!filter || c.id != filter) {
				def listName = ""
				level.times { listName += "- " }
				listName += c.name
				println "Prepping List Name ${listName}"
				list << [name: listName, value: c.properties[value]]
				list += SpudMenuItem.optionsTreeForItem(menu,[collection: itemCollection, parentId: c.id, level: level+1, filter:filter, parentType: "SpudMenuItem"])
			}
  	}
  	if(parentType == 'SpudMenu' && collection[parentType]) {
  		itemCollection = collection['SpudMenuItem'] ? collection['SpudMenuItem'].groupBy{it.parentId} : []
  		collection[parentType].each(recursiveCollectionClosure)
  	} else if(collection[parentId]) {
  		collection[parentId].each(recursiveCollectionClosure)
  	}
  	return list
  }

  static grouped(menu) {
  	menu.menuItemsCombined.groupBy { it.parentType }
  }
}
