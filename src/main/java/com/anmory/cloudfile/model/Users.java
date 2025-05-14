package com.anmory.cloudfile.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-12 下午9:55
 */

@Data
public class Users {
    private int id;
    private String username;
    private String password;
    private Date createdAt;
    String email;
    String token;
}
