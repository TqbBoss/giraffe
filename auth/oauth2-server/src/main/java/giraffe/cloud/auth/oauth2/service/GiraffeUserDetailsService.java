package giraffe.cloud.auth.oauth2.service;

import giraffe.auth.db.domains.CloudUsers;
import giraffe.auth.db.mappers.CloudUsersDao;
import giraffe.cloud.auth.oauth2.common.GiraffeUserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

@Service
public class GiraffeUserDetailsService implements UserDetailsService {
    @Resource
    private CloudUsersDao cloudUsersDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CloudUsers cloudUsers = this.cloudUsersDao.getUserByName(username);
        if (cloudUsers == null){
            throw new UsernameNotFoundException(String.format("用户 %s 不存在!", username));
        }
        User user =  new User(
                cloudUsers.getName(),
                cloudUsers.getPassword(),
                new ArrayList<>());
        return new GiraffeUserDetails(cloudUsers, user);

    }
}
