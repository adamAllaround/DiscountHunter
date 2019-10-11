package com.allaroundjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
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
    private Set<WebPagePriceDetails> priceDetails;
    @Column(nullable = false)
    private String url;

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
