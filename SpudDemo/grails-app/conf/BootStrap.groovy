import spud.security.*
class BootStrap {

    def init = { servletContext ->

    	// def user = SpudUser.findByLogin("admin")
    	// if(!user) {
    	// 	user = new SpudUser(login: "admin", email: 'destes@bcap.com', password: 'password', passwordConfirmation: 'password', superAdmin: true)
    	// }


    	// user.save(flush:true, failOnError:true)

    }
    def destroy = {
    }
}
