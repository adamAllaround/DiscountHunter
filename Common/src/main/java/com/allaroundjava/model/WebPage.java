package com.allaroundjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class WebPage extends ModelBase {
    @ManyToOne (optional = false)
    @JoinColumn(name = "USER_ID")
    private User userOwner;
    private String url;
}