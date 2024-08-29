package com.webflux.demo.entity;

import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import com.webflux.demo.roles.Roles;

import lombok.Data;

@Table("authority")
@Data
public class Authority {
    @Id
    private int id;

    private Roles role;

    @Transient
    private Timestamp date;
}
