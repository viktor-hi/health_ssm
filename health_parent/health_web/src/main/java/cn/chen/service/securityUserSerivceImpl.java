package cn.chen.service;

import cn.chen.pojo.Permission;
import cn.chen.pojo.Role;
import cn.chen.pojo.User;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author haixin
 * @time 2019-10-14
 */
@Service("securityUserSerivceImpl")
public class securityUserSerivceImpl implements UserDetailsService {

    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUsername(username);
        if (null == user) {
            return null;
        }
        //授权
        ArrayList<GrantedAuthority> authoritiesList = new ArrayList<>();
        Set<Role> userRoles = user.getRoles();

        if (null != userRoles && userRoles.size() > 0) {
            GrantedAuthority authority = null;
            for (Role userRole : userRoles) {
                authority = new SimpleGrantedAuthority(userRole.getKeyword());
                authoritiesList.add(authority);
                Set<Permission> permissions = userRole.getPermissions();
                if (null != permissions && permissions.size() > 0) {
                    for (Permission permission : permissions) {
                        authority = new SimpleGrantedAuthority(permission.getKeyword());
                        authoritiesList.add(authority);
                    }
                }
            }
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authoritiesList);
    }
}
