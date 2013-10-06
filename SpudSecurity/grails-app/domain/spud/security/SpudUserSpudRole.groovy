package spud.security

import org.apache.commons.lang.builder.HashCodeBuilder

class SpudUserSpudRole implements Serializable {

	SpudUser spudUser
	SpudRole spudRole

	boolean equals(other) {
		if (!(other instanceof SpudUserSpudRole)) {
			return false
		}

		other.spudUser?.id == spudUser?.id &&
			other.spudRole?.id == spudRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (spudUser) builder.append(spudUser.id)
		if (spudRole) builder.append(spudRole.id)
		builder.toHashCode()
	}

	static SpudUserSpudRole get(long spudUserId, long spudRoleId) {
		find 'from SpudUserSpudRole where spudUser.id=:spudUserId and spudRole.id=:spudRoleId',
			[spudUserId: spudUserId, spudRoleId: spudRoleId]
	}

	static SpudUserSpudRole create(SpudUser spudUser, SpudRole spudRole, boolean flush = false) {
		new SpudUserSpudRole(spudUser: spudUser, spudRole: spudRole).save(flush: flush, insert: true)
	}

	static boolean remove(SpudUser spudUser, SpudRole spudRole, boolean flush = false) {
		SpudUserSpudRole instance = SpudUserSpudRole.findByUserAndRole(spudUser, spudRole)
		if (!instance) {
			return false
		}

		instance.delete(flush: flush)
		true
	}

	static void removeAll(SpudUser spudUser) {
		executeUpdate 'DELETE FROM SpudUserSpudRole WHERE spudUser=:spudUser', [spudUser: spudUser]
	}

	static void removeAll(SpudRole spudRole) {
		executeUpdate 'DELETE FROM SpudUserSpudRole WHERE spudRole=:spudRole', [spudRole: spudRole]
	}

	static mapping = {
		id composite: ['spudRole', 'spudUser']
		version false
	}
}
