package com.anmory.cloudfile.service;

import com.anmory.cloudfile.mapper.AiHistoryMapper;
import com.anmory.cloudfile.model.AiHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-14 上午1:43
 */

@Service
public class AiHistoryService {
    @Autowired
    AiHistoryMapper AiHistoryMapper;

    public int insert(int userId, String prompt, String response) {
        return AiHistoryMapper.insert(userId, prompt, response);
    }

    public List<AiHistory> selectAllById(int userId) {
        return AiHistoryMapper.selectAllById(userId);
    }
}
