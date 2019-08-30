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

		def subItems = SpudMenuItem.where { parentType == 'SpudMenuItem' && parentId == menuItem.id }.list()

		subItems.each { subItem ->
			subItem.discard()
			removeMenuItem(subItem)
			subItem.delete()
		}
	}

	// Returns an array of menu items in order of heirarchy
	// :filter Filters out a menu item by ID, and all of its children
	// :value Pick an attribute to be used in the value field, defaults to ID
	def optionsTreeForItem(menu, config = [:]) {
		def collection = config.collection ?: SpudMenuItem.grouped(menu)
		def level = config.level ?: 1
		def parentId = config.parentId
		def parentType = config.parentType ?: 'SpudMenu'
		def filter = config.filter
		def value = config.value ?: "id"
		def list = []
		def itemCollection = collection
		def recursiveCollectionClosure = { c ->
			if(!filter || c.id != filter) {
				def listName = ""
				level.times { listName += "- " }
				listName += c.name
				list << [name: listName, value: c.properties[value]]
				list += optionsTreeForItem(menu, [collection: itemCollection, parentId: c.id, level: level + 1, filter: filter, parentType: "SpudMenuItem"])
			}
		}
		if(parentType == 'SpudMenu' && collection[parentType]) {
			itemCollection = collection['SpudMenuItem'] ? collection['SpudMenuItem'].groupBy { it.parentId } : []
			collection[parentType].each(recursiveCollectionClosure)
		} else if(collection[parentId]) {
			collection[parentId].each(recursiveCollectionClosure)
		}
		return list
	}

}
