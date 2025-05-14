package com.anmory.cloudfile.service;

import com.anmory.cloudfile.mapper.UsersMapper;
import com.anmory.cloudfile.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-12 下午10:00
 */

@Service
public class UsersService {
    @Autowired
    UsersMapper usersMapper;
    private static UsersService usersService;

    @Autowired
    private UsersService(UsersMapper usersMapper) {
        this.usersMapper = usersMapper;
        UsersService.usersService = this;
    }

    public static UsersService getInstance() {
        return usersService;
    }

    public int findUserByName(String username) {
        return usersMapper.findUserByName(username);
    }

    public List<Users> selectAll() {
        return usersMapper.selectAll();
    }

    public int insert(String username, String password, String email) {
        return usersMapper.insert(username, password, email);
    }

    public int updateToken(String token, int id) {
        return usersMapper.updateToken(token, id);
    }

    public String findTokenByUsername(String token) {
        return usersMapper.findUserByToken(token);
    }

    public Users findByUsername(String username) {
        return usersMapper.findByUsername(username);
    }
}
