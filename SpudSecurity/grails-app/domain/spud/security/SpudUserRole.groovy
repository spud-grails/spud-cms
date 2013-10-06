package spud.security

import org.apache.commons.lang.builder.HashCodeBuilder

class SpudUserRole implements Serializable {

   private static final long serialVersionUID = 1

   SpudUser user
   SpudRole role

   boolean equals(other) {
      if (!(other instanceof SpudUserRole)) {
         return false
      }

      other.user?.id == user?.id &&
         other.role?.id == role?.id
   }

   int hashCode() {
      def builder = new HashCodeBuilder()
      if (user) builder.append(user.id)
      if (role) builder.append(role.id)
      builder.toHashCode()
   }

   static SpudUserRole get(long userId, long roleId) {
      SpudUserRole.where {
         user == SpudUser.load(userId) &&
         role == SpudRole.load(roleId)
      }.get()
   }

   static SpudUserRole create(SpudUser user, SpudRole role, boolean flush = false) {
      new SpudUserRole(user: user, role: role).save(flush: flush, insert: true)
   }

   static boolean remove(SpudUser u, SpudRole r, boolean flush = false) {
      int rowCount = SpudUserRole.where {
         user == SpudUser.load(u.id) &&
         role == SpudRole.load(r.id)
      }.deleteAll()

      rowCount > 0
   }

   static void removeAll(SpudUser u) {
      SpudUserRole.where {
         user == SpudUser.load(u.id)
      }.deleteAll()
   }

   static void removeAll(SpudRole r) {
      SpudUserRole.where {
         role == SpudRole.load(r.id)
      }.deleteAll()
   }

   static mapping = {
      id composite: ['role', 'user']
      version false
   }
}
