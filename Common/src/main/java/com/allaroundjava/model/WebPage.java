package com.allaroundjava.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class WebPage extends ModelBase {
    @ManyToOne(optional = false)
    @JoinColumn(name = "USER_ID")
    private User userOwner;
    @OneToMany(mappedBy = "page")
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private Set<WebPagePriceDetails> priceDetails = new LinkedHashSet<>();
    @Column(nullable = false)
    private String url;

    private BigDecimal priceProposal;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebPage webPage = (WebPage) o;

        if (userOwner != null ? !userOwner.equals(webPage.userOwner) : webPage.userOwner != null) return false;
        return url.equals(webPage.url);
    }

    @Override
    public int hashCode() {
        int result = userOwner != null ? userOwner.hashCode() : 0;
        result = 31 * result + url.hashCode();
        return result;
    }
}
