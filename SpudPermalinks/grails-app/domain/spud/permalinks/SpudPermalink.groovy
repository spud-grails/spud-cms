package spud.permalinks

class SpudPermalink {
	String urlName
	String attachmentType
	Long attachmentId

	String destinationUrl

	Date dateCreated
	Date lastUpdated

  static constraints = {
  	url nullable: true
  }

	static mapping = {
		table 'spud_permalinks'
		autoTimestamp true
		dateCreated column: 'created_at'
		lastUpdated column: 'updated_at'

		// TODO: Add Compound index for attachmentType, attachmentId
	}
}
