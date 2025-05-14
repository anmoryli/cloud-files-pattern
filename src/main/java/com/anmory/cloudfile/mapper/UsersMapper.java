package com.anmory.cloudfile.mapper;

import com.anmory.cloudfile.model.Users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-05-12 下午9:57
 */

@Mapper
public interface UsersMapper {
    @Select("select * from users where username = #{username}")
    Users findByUsername(String username);

    @Insert("insert into users (username, password, email) values (#{username}, #{password}, #{email})")
    int insert(String username, String password, String email);

    @Select("select users.id from users where username = #{username}")
    int findUserByName(String username);

    @Select("select * from users")
    List<Users> selectAll();

    @Update("update users set token = #{token} where id = #{id}")
    int updateToken(String token, int id);

    @Select("select * from users where token = #{token}")
    String findUserByToken(String token);
}
