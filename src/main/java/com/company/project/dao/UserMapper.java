package com.company.project.dao;

import com.company.project.core.Mapper;
import com.company.project.model.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends Mapper<User> {
    @Select("select * from user where id = #{id};")
    public User selectById(Integer id);


    @Select("select * from user where username = #{username};")
    public User selectByUsername(String username);
}