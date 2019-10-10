package com.allaroundjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class WebPagePriceDetails {
    @ManyToOne(optional = false)
    @JoinColumn(name = "PAGE_ID")
    private WebPage page;
    private String price;
}
