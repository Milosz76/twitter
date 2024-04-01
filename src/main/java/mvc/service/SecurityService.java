package mvc.service;

import mvc.model.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.Collection;

@Component("securityService")
public class SecurityService {

    public boolean hasPermission(Permission...permissions){
        Collection<? extends GrantedAuthority> userAuthorities = SecurityContextHolder
                                                                    .getContext()
                                                                    .getAuthentication()
                                                                    .getAuthorities();

        for (Permission perm : permissions){
            if(userAuthorities.contains(new SimpleGrantedAuthority(perm.name())));
            return true;
        }
        return false;
    }

}
