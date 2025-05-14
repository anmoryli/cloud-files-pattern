package com.anmory.cloudfile.service;

import com.anmory.cloudfile.mapper.FilesMapper;
import com.anmory.cloudfile.model.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-12 下午10:07
 */

// Service本身就是单例，不需要手动实现
@Service
public class FilesService {
    @Autowired
    FilesMapper filesMapper;
    private static FilesService filesService;

    @Autowired
    private FilesService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
        FilesService.filesService = this;
    }
    public static FilesService getInstance() {
        return filesService;
    }

    public List<Files> findByUserId(int userId) {
        return filesMapper.findByUserId(userId);
    }

    public Files findById(int id) {
        return filesMapper.findById(id);
    }

    public int insert(String name, String size, String type, String path, int userId) {
        return filesMapper.insert(name, size, type, path, userId);
    }

    public int deleteById(int id) {
        return filesMapper.delete(id);
    }
}
