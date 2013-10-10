package spud.cms

class SpudPagePartial {
	static belongsTo = [page: SpudPage]
	String name
	String symbolName
	String content
	String contentProcessed
	String format="html"

	Date dateCreated
	Date lastUpdated

	static mapping = {
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


	public String getContentProcessed() {
		if(properties.contentProcessed) {
			return properties.contentProcessed
		}
		// TODO : Find out if a renderer / formatter is needed on the content
		return content
	}
}

