package com.example.pillmanage.config.entity;

import lombok.Data;

/**
 * @ClassName SellInfo
 * @Package com.example.pillmanage.config.entity
 * @Author Hang Zhao
 * @Description 经销商传入的信息
 * @Date 2022/12/20 14:31
 */
@Data
public class SellInfo {

    private String traceno;

    private String s_number;

    private String s_shop_name;

    private String s_name;

    private String s_tel;

    private String s_price;

    private String s_remark;

    private String seller_id;

}
