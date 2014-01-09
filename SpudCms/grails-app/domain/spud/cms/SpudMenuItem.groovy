package spud.cms

class SpudMenuItem {
	String name
	String parentType
	Long   parentId

	Integer menuOrder = 0
	String url
	SpudPage page

	SpudMenu menu


	Date dateCreated
	Date lastUpdated

	static mapping = {
		table 'spud_menu_items'
		autoTimestamp true

		menu column: 'spud_menu_id'
		page column: 'spud_page_id'

		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'

		// parentType index: 'idx_menu_item_parent'
		// parentId   index: 'idx_menu_item_parent'
		// TODO Add Multi column index for parent in right order
	}
  static constraints = {
  	name       blank:false
  	menu       nullable: false
  	parentType blank: false
  	parentId   blank: false
  	url        nullable: true
  	page       nullable: true
  	menuOrder  nullable: true

  }
}
