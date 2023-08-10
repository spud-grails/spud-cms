package spud.cms

class SpudPagePartial {
	def spudTemplateService
	def grailsApplication

	static belongsTo = [page: SpudPage]
	static transients = ['cachedContent', 'postContent']
	String name
	String symbolName
	String content
	String contentProcessed
	String format="html"

	String cachedContent

	Date dateCreated
	Date lastUpdated

	static mapping = {
		def cfg = it?.getBean('grailsApplication')?.config
		datasource(cfg?.spud?.core?.datasource ?: 'DEFAULT')

		cache true
		table 'spud_page_partials'
		autoTimestamp true
		content type:'text'
		contentProcessed type:'text'
		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'
	}

	static constraints = {
		contentProcessed nullable: true, maxSize: 65000
		content nullable:true, maxSize: 65000
		symbolName blank: false
		name blank:false
	}

	public void setName(String name) {
		this.name = name
		// this.symbolName = name.replaceAll(" ", "_").replaceAll(":","_").replaceAll("-","_").replaceAll(",","_").toLowerCase()
	}

	public void setPostContent(String _content) {
		content = _content
		contentProcessed = null
	}

	public String getPostContent() {
		return content
	}

	def beforeValidate() {
		if(this.content && !this.contentProcessed) {
			def formatter = grailsApplication.config.spud.formatters.find{ it.name == this.format}?.formatterClass
			log.debug "beforeValidate formatter: ${formatter}"
			if(formatter) {
				grailsApplication.classLoader.loadClass(formatter)
				Class.forName(formatter).newInstance()
//				def formattedText = formatter.newInstance().compile(this.content)
				def formattedText = Class.forName(formatter).newInstance().compile(this.content)
				log.debug "beforeValidate formattedText: ${formattedText}"
				contentProcessed = formattedText
			} else {
				contentProcessed = this.content
			}
		}
	}

	public String render() {
		if(cachedContent) {
			return cachedContent
		}
		cachedContent = spudTemplateService.render("${page.name}.${name}",contentProcessed ?: content,[model: [page:page]])
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
