package spud.cms
import spud.permalinks.*

class SpudPage {
	static hasMany = [ partials:SpudPagePartial ]
	static transients = ['pages']
	
	def spudPermalinkService

	Integer siteId = 0
	SpudPage spudPage //Parent Page if it has one
	String name
	String urlName
	Boolean useCustomUrlName = false

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
		cache true
		table 'spud_pages'
		autoTimestamp true
		notes type: 'text'
		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'
		partials cache: true
		siteId index: 'idx_page_url'
		urlName index: 'idx_page_url'
	}

	static constraints = {
		name blank:false
		urlName nullable: false, unique: 'siteId'
		notes nullable: true
		layout nullable: true
		metaDescription nullable:true
		metaKeywords nullable:true
		spudPage nullable:true
		createdBy nullable: true
		updatedBy nullable: true
		publishAt nullable: true
	}




	Set<SpudPage> getPages() {
		SpudPage.where{ spudPage == this}.list(sort: 'pageOrder')
	}


	static grouped(siteId=0) {
		return SpudPage.findAllBySiteId(siteId).groupBy{it.spudPageId}
	}

}

