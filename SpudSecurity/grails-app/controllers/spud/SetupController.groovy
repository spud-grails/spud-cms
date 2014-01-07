package spud
import  spud.core.*
import  spud.security.*

class SetupController {
	static namespace = 'spud_admin'

	def spudSecurity

	def create() {
		// @page_thumbnail = "spud/admin/users_thumb.png"
		// @page_name = "First Time Setup"
		if(SpudUser.count() != 0) {
			flash.error = "Access Denied! This wizard may only be executed when the database is empty."
			redirect spudSecurity.loginUrl
			return
		}
		render view: '/spud/setup/create', model: [user: new SpudUser()]
	}

	def save() {
		if(SpudUser.count() != 0) {
			flash.error = "Access Denied! This wizard may only be executed when the database is empty."
			redirect spudSecurity.loginUrl
			return
		}
		def user = new SpudUser(login: params.login,email: params.email,password: params.password,passwordConfirmation: params.passwordConfirmation)
		user.superAdmin = true
		if(user.save(flush:true)) {
			redirect spudSecurity.loginUrl
		} else {
			flash.error = "Error creating administrative account!"
			render view: '/spud/setup/create', model: [user: user]
		}

	}
}
