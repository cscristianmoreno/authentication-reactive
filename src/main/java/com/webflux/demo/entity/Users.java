package com.webflux.demo.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import com.webflux.demo.roles.Roles;

import lombok.Data;
import lombok.ToString;

@Table("users")
@Data
@ToString
public class Users {
    @Id
    private int id; 

    private String name;
    private String lastname;
    private int age;
    private String username;
    private String password;
    
    private List<Roles> role = new ArrayList<Roles>();

    @Transient
    private Timestamp date;

    public void setRole(Roles... roles) {
        
        for (Roles rol: roles) {
            role.add(rol);
        }
    }
}
