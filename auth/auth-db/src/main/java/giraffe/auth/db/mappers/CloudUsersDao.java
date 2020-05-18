package giraffe.auth.db.mappers;

import giraffe.auth.db.domains.CloudUsers;

public interface CloudUsersDao {
    int deleteByPrimaryKey(Long id);

    int insert(CloudUsers record);

    int insertSelective(CloudUsers record);

    CloudUsers selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CloudUsers record);

    int updateByPrimaryKey(CloudUsers record);

    CloudUsers getUserByName(String userName);
}