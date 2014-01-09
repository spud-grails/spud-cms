package spud.permalinks

class SpudPermalink {

	Integer siteId = 0
	String urlName
	String attachmentType
	Long attachmentId

	String destinationUrl

	Date dateCreated
	Date lastUpdated

  static constraints = {
  	attachmentType nullable: true
  	attachmentId nullable: true
  	siteId index: true
  	// TODO: Create Index Compound on attachmentType, attachmentId
  }

	static mapping = {
		table 'spud_permalinks'
		autoTimestamp true
		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'

		// TODO: Add Compound index for attachmentType, attachmentId
	}
}
