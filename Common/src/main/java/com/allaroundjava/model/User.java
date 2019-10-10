package com.allaroundjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class User extends ModelBase {
    private String email;
    private String password;
    private boolean enabled;
}
