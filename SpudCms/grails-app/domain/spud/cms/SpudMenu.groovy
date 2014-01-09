package spud.cms

class SpudMenu {
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
  }
}
