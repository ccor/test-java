/*
*
* UserMapper.java
* Copyright(C) 2017-2020 fendo公司
* @date 2020-08-24
*/
package x.xx.dao.mysql;

import x.xx.domain.mysql.User;

/**
* Created by Mybatis Generator 2020/08/24
*/
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}