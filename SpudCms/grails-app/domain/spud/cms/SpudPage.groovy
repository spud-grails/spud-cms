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
		notes nullable: true
		layout nullable: true
		metaDescription nullable:true
		metaKeywords nullable:true
		createdBy nullable: true
		updatedBy nullable: true
		publishAt nullable: true
	}

	def beforeValidate() {
		generateUrlName()
	}

	private generateUrlName() {
		if(!name) {
			return true //Throw Name validation error instead of generated url name
		}

		def urlNamePrefix = ""
		if(spudPage) {
			urlNamePrefix += spudPage.urlName + "/"
		}

		if(!this.userCustomUrlName || !this.urlName) { //If we need to generate a url name
			urlName = name.replaceAll(" ", "-").replaceAll(":","-").replaceAll("-","-").replaceAll(",","-").toLowerCase()
			def urlNames = SpudPage.createCriteria().list {
				ne('id', this.id)
				eq('siteId', this.siteId)
				projections {
					property('urlName')
				}
			}

			def counter = 1
			def urlNameNew = urlName
			while(urlNames.contains(urlNamePrefix + urlNameNew)) {
				urlNameNew = urlName + "-${counter}"
				counter += 1
			}

			urlName = urlNamePrefix + urlNameNew
			// TODO: Verify Does not exist in Permalink Database

		}

	}
}

