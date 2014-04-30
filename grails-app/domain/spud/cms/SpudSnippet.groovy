package spud.cms

class SpudSnippet {
	Integer siteId = 0
	String name

	String content
	String contentProcessed
	String format = 'html'

	Date dateCreated
	Date lastUpdated

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

  public String getContentProcessed() {
  	// TODO : Precompile Content based on format
  	return this.content
  }
}
