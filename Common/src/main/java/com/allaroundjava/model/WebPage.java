package com.allaroundjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
public class WebPage extends ModelBase {
    @ManyToOne (optional = false)
    @JoinColumn(name = "USER_ID")
    private User userOwner;
    @OneToMany (mappedBy = "page")
    private Set<WebPagePriceDetails> webPage;
    private String url;
}
