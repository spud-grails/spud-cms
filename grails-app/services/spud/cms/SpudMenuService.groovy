package spud.cms

import spud.core.*

class SpudMenuService {
	def grailsApplication
	def spudPermalinkService

	def removeMenuItems(menuItems) {
		menuItems.each { menuItem ->
			menuItem.discard()
			removeMenuItem(menuItem)
		}
	}
	def removeMenuItem(menuItem) {
		// We need to remove all associated objects before removing the page

		def subItems = SpudMenuItem.where{ parentType == 'SpudMenuItem' && parentId == menuItem.id}.list()

		subItems.each { subItem ->
			subItem.discard()
			removeMenuItem(subItem)
			subItem.delete()
		}
	}
}
