package com.anmory.cloudfile.model;

import lombok.Data;

import java.util.Date;

/**
 * @author Anmory
 * @description TODO
 * @date 2025-05-14 上午1:41
 */

@Data
public class AiHistory {
    private int id;
    private int userId;
    private String prompt;
    private String response;
    private Date createAt;
}
