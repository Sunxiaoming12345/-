package com.example.gateway.mapper;

import com.example.gateway.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username} and password=#{password}")
    User login(@Param("username") String username, @Param("password") String password);
    
    @Select("select * from user where username=#{username}")
    User findByUsername(@Param("username") String username);
    
    @Insert("insert into user(name, username, password, phone, create_time, update_time) values(#{name}, #{username}, #{password}, #{phone}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);
}
