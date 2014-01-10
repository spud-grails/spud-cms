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
		urlName nullable:true
		notes nullable: true
		layout nullable: true
		metaDescription nullable:true
		metaKeywords nullable:true
		spudPage nullable:true
		createdBy nullable: true
		updatedBy nullable: true
		publishAt nullable: true
		pages cascade: 'evict'
	}

	def beforeUpdate() {
		generateUrlName()
	}
	def beforeInsert() {
		generateUrlName()
	}

	def beforeDelete() {
		this.pages = []
		SpudPermalink.withNewSession { session ->
			spudPermalinkService.deletePermalinksForAttachment(this, this.siteId)

			def page = SpudPage.read(this.id)
			def menus = SpudMenuItem.findAllByPage(page)
			menus.each { menu ->
				menu.page = null
				menu.save(flush:true)
			}
			// def pages = SpudPage.findAllBySpudPage(page)

		}
	}

	static grouped(siteId=0) {
		return SpudPage.findAllBySiteId(siteId).groupBy{it.spudPageId}
	}


	// Returns an array of pages in order of heirarchy
	// 	:filter Filters out a page by ID, and all of its children
	//  :value Pick an attribute to be used in the value field, defaults to ID
	static optionsTreeForPage(config=[:]) {
		def collection = config.collection ?: SpudPage.grouped(config.siteId ?: 0)
		def level      = config.level ?: 0
		def parentId   = config.parentId
		def filter     = config.filter
		def value      = "id"
		def list       = []

		collection[parentId]?.each { c ->
			if(!filter || c.id != filter) {
				def listName = ""
				level.times { listName += "- " }
				listName += c.name
				list << [name: listName, value: c[value]]
				list += SpudPage.optionsTreeForPage([collection: collection, parentId: c.id, level: level+1, filter: filter])
			}
		}
		return list
	}




	private uniqueUrlName() {
			def urlNamePrefix = ""
			def urlName = name.replaceAll(" ", "-").replaceAll(":","-").replaceAll("-","-").replaceAll(",","-").toLowerCase()
			if(spudPage) {
				urlNamePrefix += spudPage.urlName + "/"
			}
			def urlNames = SpudPage.createCriteria().list {
				ne('id', this.id)
				eq('siteId', this.siteId)
				projections {
					property('urlName')
				}
			}

			def counter = 1
			def currentCounter = 0
			def urlNameNew = urlName
			while(urlNames.contains(urlNamePrefix + urlNameNew)) {
				urlNameNew = urlName + "-${counter}"
				currentCounter = counter
				counter += 1
			}
			return [urlName: urlName, counter: currentCounter]
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
			def uniqueName = uniqueUrlName()
			urlName = uniqueName.urlName
			def urlNameNew = urlName
			if(uniqueName.counter > 0 ) {
				urlNameNew = uniqueName.urlName + "-${uniqueName.counter}"
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

