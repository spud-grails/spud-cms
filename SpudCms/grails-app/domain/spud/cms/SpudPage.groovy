package spud.cms

class SpudPage {
	Integer siteId = 0
	SpudPage spudPage //Parent Page if it has one
	static hasMany = [pages: SpudPage, partials:SpudPagePartial ]

	String name
	String urlName
	Boolean userCustomUrlName = false

	String layout
	Integer visibility = 0 //0=Public, 1=Private
	Integer pageOrder  = 0
	Boolean published  = true

	String metaDescription
	String metaKeywords
	String notes

	String createdBy
	String updatedBy


	String renderer = 'gsp'
	String templateEngine = 'system'

	Date publishAt
	Date dateCreated
	Date lastUpdated

	static mapping = {
		table 'spud_pages'
		autoTimestamp true
		notes type: 'text'
		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'
	}

	static constraints = {
		name blank:false
		urlName blank:false
		layout nullable: true
		metaDescription nullable:true
		metaKeywords nullable:true
		createdBy nullable: true
		updatedBy nullable: true
		publishAt nullable: true
	}
}

