package spud.cms

class SpudMenu {
	static hasMany = [menuItemsCombined: SpudMenuItem]

	static transients = ['menuItems']

	Integer siteId = 0
	String name
	String description


	Date dateCreated
	Date lastUpdated


	static mapping = {
		table 'spud_menus'
		autoTimestamp true
		description type:'text'

		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'
	}
  static constraints = {
  	name blank:false, unique: 'siteId'
  	description nullable:true
  	menuItemsCombined  cascade: 'all-delete-orphan'
  }

  Set<SpudMenuItem> getMenuItems() {
  	if(id) {
  		return SpudMenuItem.withCriteria(readOnly:true) {
  			eq('parentType','SpudMenu')
  			eq('parentId', id)
  			order('menuOrder')
  		} as Set
  	}
  	return null
  }
}
