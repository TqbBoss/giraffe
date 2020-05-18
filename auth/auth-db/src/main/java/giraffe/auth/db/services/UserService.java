package giraffe.auth.db.services;

import giraffe.auth.db.domains.CloudUsers;
import giraffe.auth.db.mappers.CloudUsersDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private CloudUsersDao cloudUsersDao;

    @Transactional
    public int addUser(CloudUsers user){
        return this.cloudUsersDao.insert(user);
    }
}
