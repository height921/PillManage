package com.example.pillmanage.config.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    public String msg;

    public int code;

    public Object data;
}
