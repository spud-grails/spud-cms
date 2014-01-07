package spud.cms
import spud.permalinks.*

class SpudPage {

	static hasMany = [pages: SpudPage, partials:SpudPagePartial ]
	def spudPermalinkService

	Integer siteId = 0
	SpudPage spudPage //Parent Page if it has one
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

	def beforeUpdate() {
		generateUrlName()
	}
	def beforeInsert() {
		generateUrlName()
	}

	def beforeDelete() {
		SpudPermalink.withNewSession { session ->
			spudPermalinkService.deletePermalinksForAttachment(this, this.siteId)
		}
	}

	private generateUrlName() {
		def original
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

			def originalUrlName = getPersistentValue('urlName')


			// Update Permalinks
			SpudPermalink.withNewSession {
				def permalink = spudPermalinkService.permalinkForUrl(urlNamePrefix + urlNameNew)
				while(permalink) {
					if(permalink.attachmentType == 'SpudPage' && permalink.attachmentId == this.id) {
						permalink.delete()
						permalink = null
					} else {
						urlNameNew = urlName + "-${counter}"
						counter += 1
						permalink = spudPermalinkService.permalinkForUrl(urlNamePrefix + urlNameNew)
					}
				}

				if(originalUrlName != null && (urlNamePrefix + urlNameNew) != originalUrlName) {
					spudPermalinkService.createPermalink(originalUrlName,this, urlNamePrefix + urlNameNew, this.siteId)
				}

				urlName = urlNamePrefix + urlNameNew
			}


		}

	}

}

