package spud.admin
import  spud.core.*


@SpudApp(name="Users", thumbnail="spud/admin/users_thumb.png")
@SpudSecure(['SUPER_ADMIN', 'USERS'])
class UsersController {

    def index = {

    }
}
