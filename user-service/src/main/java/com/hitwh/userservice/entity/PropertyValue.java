package com.hitwh.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyValue {
    private int id;
    private int pid;
    private int ptid;
    private String value;
}
