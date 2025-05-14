package com.anmory.cloudfile.mapper;

import com.anmory.cloudfile.model.Files;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-05-12 下午10:01
 */

@Mapper
public interface FilesMapper {
    @Select("select * from files where user_id = #{userId}")
    List<Files> findByUserId(int userId);

    @Select("select * from files where id = #{id}")
    Files findById(int id);

    @Insert("insert into files (name, size, type, path, user_id) values " +
            "(#{name}, #{size}, #{type}, #{path}, #{userId})")
    int insert(String name, String size, String type, String path, int userId);

    @Delete("delete from files where id = #{id}")
    int delete(int id);
}
