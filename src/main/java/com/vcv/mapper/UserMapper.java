package com.vcv.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.vcv.model.User;


@Mapper
public interface UserMapper {

    User selectByNameAndPwd(User user);

    int insert(User user);

    int update(User user);

    int selectIsName(User user);

    String selectPasswordByName(User user);
}
