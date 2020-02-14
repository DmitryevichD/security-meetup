package by.dm13y.examples.security.config.ldap

import by.dm13y.example.securityexample.config.UserDetailInfo
import by.dm13y.examples.security.config.Roles
import org.springframework.ldap.core.DirContextAdapter
import org.springframework.ldap.core.DirContextOperations
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper
import org.springframework.stereotype.Component

@Component
class LdapUserMapper: LdapUserDetailsMapper() {
    override fun mapUserFromContext(ctx: DirContextOperations?, username: String?, authorities: MutableCollection<out GrantedAuthority>?): UserDetails {
        return UserDetailInfo("ldap", Roles.LDAP.name)
        //Create user details from ldap info
    }

    override fun mapUserToContext(user: UserDetails?, ctx: DirContextAdapter?) {
        super.mapUserToContext(user, ctx)
        //map user details to DirContext for save info to AD
    }
}
