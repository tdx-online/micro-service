package com.hitwh.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultInfo implements Serializable {
    private boolean flag;
    private Object data;
    private String errorMsg;
}
