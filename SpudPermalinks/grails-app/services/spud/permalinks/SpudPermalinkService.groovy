package spud.permalinks

import grails.transaction.Transactional

@Transactional
class SpudPermalinkService {

  def permalinksForObject(attachment) {
  	def objectType = attachment.class.name
  	def objectId   = attachment.id

  	def permalinks = SpudPermalink.createCriteria().list {
  		eq('attachmentType', objectType)
  		eq('attachmentId', objectId)
  	}

  	return permalinks
  }

  def createPermalink(url, attachment, destinationUrl) {
  	def objectType = attachment.class.name
  	def objectId   = attachment.id

  	// Clear out any permalinks for the destinationUrl
  	def destinationPerms = SpudPermalink.findAllByUrlName(destinationUrl)
  	destinationPerms?.each { permalink ->
  		permalink.delete(flush:true)
  	}

  	// Check if permalink already exists
  	def permalink = SpudPermalink.findByUrlName(url)

  	if(!permalink) {
			permalink = new SpudPermalink(url: url)
  	}
  	permalink.attachmentType = objectType
		permalink.attachmentId   = objectId
		permalink.destinationUrl = destinationUrl

		return permalink.save(flush: true)
  }

}
