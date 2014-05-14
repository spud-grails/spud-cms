package spud.cms

class SpudSnippet {
	def grailsApplication
	def spudTemplateService

	static transients = ['cachedContent']

	Integer siteId = 0
	String name
	String content
	String contentProcessed
	String format = 'html'

	Date dateCreated
	Date lastUpdated

	//Transients
	String cachedContent

	static mapping = {
		def cfg = it?.getBean('grailsApplication')?.config
		datasource(cfg?.spud?.core?.datasource ?: 'DEFAULT')

		table 'spud_snippets'
		autoTimestamp true
		content type:'text'
		contentProcessed type:'text'
		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'
	}
  static constraints = {
  	name blank:false
  	content nullable:true
  	contentProcessed nullable:true
  }

	public void setContent(String _content) {
		content = _content
		contentProcessed = null
	}


	public String render() {
		if(cachedContent) {
			return cachedContent
		}
		cachedContent = spudTemplateService.render("_${this.name}",contentProcessed ?: content,[model: [snippet:this]])
	}

	public String getRender() {
		this.render()
	}

	def beforeValidate() {
		if(this.content && !this.contentProcessed) {
			def formatter = grailsApplication.config.spud.formatters.find{ it.name == this.format}?.formatterClass
			if(formatter) {
				def formattedText = formatter.newInstance().compile(this.content)
				contentProcessed = formattedText
			} else {
				contentProcessed = this.content
			}
		}
	}
}
