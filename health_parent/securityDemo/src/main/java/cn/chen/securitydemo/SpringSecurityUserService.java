package cn.chen.securitydemo;

import cn.chen.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haixin
 * @time 2019-10-14
 */
public class SpringSecurityUserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询数据库，获得user
        User user = findUserByName(username);

        if (null == user) {
            return null;
        }
        //3.把用户名,数据库的密码,以及查询出来的权限访问,创建UserDetails对象返回
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();//先把角色和权限写死,后面从数据库查询出来
        list.add(new SimpleGrantedAuthority("add"));
        list.add(new SimpleGrantedAuthority("delete"));
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(username, "{noop}"+user.getPassword(), list);
        return userDetails;
    }

    //模拟从数据库查询
    private User findUserByName(String username){
        if ("admin".equals(username)) {
            User user = new User();
            user.setUsername(username);
            user.setPassword("123456");
            return user;
        }
        return null;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("admin"));
        System.out.println(bCryptPasswordEncoder.matches("1234","$2a$10$oFuWn5IRrIiB09ijLmi6zuCRT0EGHs2SRCE.nxum4Mj1RU5eTaHLi"));
    }
}
