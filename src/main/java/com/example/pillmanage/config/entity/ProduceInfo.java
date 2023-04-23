package com.example.pillmanage.config.entity;

import lombok.Data;

/**
 * @ClassName ProduceInfo
 * @Package com.example.pillmanage.config.entity
 * @Author Hang Zhao
 * @Description TODO
 * @Date 2022/12/19 21:43
 */
@Data
public class ProduceInfo {

    private String name;

    private String p_number;

    private String p_ingredient;

    private String p_package_size;

    private String p_time;

    private String p_validity_time;

    private String p_batch_number;

    private String p_approval_number;

    private String p_name;

    private String p_address;

    private String p_tel;

    private String p_remark;

    private Integer p_count;

    private String producer_id;
}
