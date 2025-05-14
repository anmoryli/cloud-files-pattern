package com.anmory.cloudfile.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-12 下午9:56
 */

@Data
public class Files {
    private int id;
    private int userId;
    private String name;
    private String size;
    private String type;
    private String path;
    private Date createdAt;
}
