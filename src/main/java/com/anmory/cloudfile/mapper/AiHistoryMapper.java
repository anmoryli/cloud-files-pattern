package com.anmory.cloudfile.mapper;

import com.anmory.cloudfile.model.AiHistory;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Anmory/李梦杰
 * @description TODO
 * @date 2025-05-14 上午1:42
 */

@Mapper
public interface AiHistoryMapper {
    @Insert("insert into ai_history (user_id, prompt, response) values (#{userId}, #{prompt}, #{response})")
    int insert(int userId, String prompt, String response);

    @Select("select * from ai_history where user_id = #{userId}")
    List<AiHistory> selectAllById(int userId);
}
