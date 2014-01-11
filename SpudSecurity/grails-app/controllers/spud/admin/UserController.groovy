package spud.admin
import  spud.core.*
import  spud.security.*

@SpudApp(name="Users", thumbnail="spud/admin/users_thumb.png")
@SpudSecure(['USERS'])
class UserController {
	static namespace = 'spud_admin'


	def index = {
		def users = SpudUser.list([max:25] + params)
		render view: '/spud/admin/users/index', model:[users: users, userCount: SpudUser.count()]
	}

	def show = {
		def user = loadUser()

		if(!user) {
			return
		}

		render view: '/spud/admin/users/show', model: [user: user]
	}

	def create = {
		def user = new SpudUser()
		render view: '/spud/admin/users/create', model:[user: user]
	}

	def save = {
		if(!params.user) {
			flash.error = "User Submission not specified"
			return
		}


		def user = new SpudUser(params.user)

		if(user.save(flush:true)) {
			redirect(resource: 'user', action: 'index', namespace: 'spud_admin')
		} else {
			flash.error = "Error saving user"
			render view: '/spud/admin/users/create', model:[user: user]
		}

	}

	def edit = {
		def user = loadUser()

		if(!user) {
			return
		}

		render view: '/spud/admin/users/edit', model: [user: user]
	}

	def update = {
		if(!params.user) {
			flash.error = "User Submission not specified"
			redirect resource: 'user', action: 'index', namespace: 'spud_admin'
			return
		}

		def user = loadUser()
		if(!user) {
			return
		}



		user.properties += params.user
		if(!user.save(flush:true)) {
			flash.error = "Error Saving User"
			render view: '/spud/admin/users/edit', model: [user: user]
			return
		}
		redirect resource: 'user', action: 'index', namespace: 'spud_admin'
	}

	def delete = {
		def user = loadUser()

		if(!user) {
			return
		}
		if(user.delete(flush:true))
		{
			flash.notice = "User successfully removed"
		} else {
			flash.error = "Unable to remove user"
		}
		redirect resource: 'user', action: 'index', namespace: 'spud_admin'
	}

	private loadUser() {
		if(!params.id) {
			flash.error = "User Submission not specified"
			redirect controller: 'user', action: 'index', namespace: 'spud_admin'
			return null
		}

		def user = SpudUser.get(params.id)
		if(!user) {
			flash.error = "User not found!"
			redirect controller: 'user', action: 'index', namespace: 'spud_admin'
			return null
		}
		return user
	}
}
