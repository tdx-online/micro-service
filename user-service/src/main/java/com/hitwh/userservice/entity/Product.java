package com.hitwh.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    protected int id;
    protected String name;
    protected String subTitle;
    protected Double originalPrice;
    protected Double promotePrice;
    protected int stock;
    protected int cid;
    protected Timestamp createDate;
}
