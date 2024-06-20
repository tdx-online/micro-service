package com.hitwh.userservice.mapper;


import com.hitwh.userservice.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
//    @Insert("insert into user(username, password, email, address, block_address) values(#{username},#{password}, #{email}, #{address},#{blockAddress})")
    @Insert("insert into user(username, password, email, address) values(#{username},#{password}, #{email}, #{address})")
    Integer addUser(User user);

    @Select("select * from user where username = #{username}")
    User getUserByUsername(String username);

    @Select("select * from user where username = #{id}")
    User getUserById(int id);

    @Select("select * from user where username = #{username} and password=#{password}")
    User getUserByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Delete("delete from user where id = #{id}")
    int deleteUserById(int id);

    @Select("select count(id) from user")
    int getTotal();

    @Select("select * from user")
    List<User> getUserList();
}
