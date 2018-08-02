package spud.cms

import spud.core.*
import spud.permalinks.*
import grails.plugin.cache.CacheEvict
import grails.plugin.cache.Cacheable

class SpudPageService {
	def grailsApplication
	def spudPermalinkService
	def spudMenuService
	def spudMultiSiteService

	def remove(page) {
		// We need to remove all associated objects before removing the page
		spudMenuService.removeMenuItems(SpudMenuItem.where { page == page }.list())

		// Partials Are cascade Removed
		// Permalinks
		spudPermalinkService.deletePermalinksForAttachment(page)

		// Move sub pages up the tree one
		SpudPage.where { spudPage == page }.updateAll(spudPage: page.spudPage)

		// Remove the actual Page
		page.delete()
	}

	def grouped(siteId = 0) {
		return SpudPage.findAllBySiteId(siteId).groupBy { it.spudPageId }
	}

	// Returns an array of pages in order of heirarchy
	// 	:filter Filters out a page by ID, and all of its children
	//  :value Pick an attribute to be used in the value field, defaults to ID
	def optionsTreeForPage(config = [:]) {
		def collection = config.collection ?: grouped(config.siteId ?: 0)
		def level = config.level ?: 0
		def parentId = config.parentId
		def filter = config.filter
		def value = "id"
		def list = []

		collection[parentId]?.each { c ->
			if(!filter || c.id != filter) {
				def listName = ""
				level.times { listName += "- " }
				listName += c.name
				list << [name: listName, value: c[value]]
				list += optionsTreeForPage([collection: collection, parentId: c.id, level: level + 1, filter: filter])
			}
		}
		return list
	}

	// Generates a unique urlName for a page before saving, This is required for cms url mappings
	def generateUrlName(page) {
		if(!page.name) {
			return
		}
		def originalUrlName = page.getPersistentValue('urlName') ?: page.urlName
		def urlNamePrefix = ""

		if(page.spudPage) {
			urlNamePrefix += page.spudPage.urlName + "/"
		}

		if(!page.useCustomUrlName || !page.urlName) { //If we need to generate a url name
			def uniqueName = uniqueUrlName(page)
			def urlName = uniqueName.urlName
			def urlNameNew = urlName
			if(uniqueName.counter > 0) {
				urlNameNew = uniqueName.urlName + "-${uniqueName.counter}"
			}

			// Update Permalinks
			SpudPermalink.withNewSession {
				def permalink = spudPermalinkService.permalinkForUrl(urlNamePrefix + urlNameNew)
				while(permalink) {
					if(permalink.attachmentType == 'SpudPage' && permalink.attachmentId == page.id) {
						permalink.delete()
						permalink = null
					} else {
						urlNameNew = urlName + "-${counter}"
						counter += 1
						permalink = spudPermalinkService.permalinkForUrl(urlNamePrefix + urlNameNew)
					}
				}

				if(originalUrlName != null && (urlNamePrefix + urlNameNew) != originalUrlName) {
					spudPermalinkService.createPermalink(originalUrlName, page, urlNamePrefix + urlNameNew, page.siteId)
				}

				page.urlName = urlNamePrefix + urlNameNew
			}
		}
	}


	@CacheEvict(value = 'spud.cms.page', allEntries = true)
	def evictCache() {
		log.info("Evicting Sitemap Cache")
	}

	private uniqueUrlName(page) {
		def urlNamePrefix = ""
		def urlName = page.name.parameterize().toLowerCase()
		if(page.spudPage) {
			urlNamePrefix += page.spudPage.urlName + "/"
		}
		def currentCounter = 0

		SpudPage.withNewSession { session ->
			def urlNames = SpudPage.createCriteria().list {
				ne('id', page.id)
				eq('siteId', page.siteId)
				projections {
					property('urlName')
				}
			}

			def counter = 1

			def urlNameNew = urlName
			while(urlNames.contains(urlNamePrefix + urlNameNew)) {
				urlNameNew = urlName + "-${counter}"
				currentCounter = counter
				counter += 1
			}
		}
		return [urlName: urlName, counter: currentCounter]
	}
}
