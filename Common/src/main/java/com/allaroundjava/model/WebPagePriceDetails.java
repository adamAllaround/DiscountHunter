package com.allaroundjava.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class WebPagePriceDetails extends ModelBase {
    @ManyToOne(optional = false)
    @JoinColumn(name = "PAGE_ID")
    private WebPage page;
    private String price;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WebPagePriceDetails that = (WebPagePriceDetails) o;

        if (!page.getId().equals(that.page.getId())) return false;
        return price.equals(that.price);
    }

    @Override
    public int hashCode() {
        int result = page.getId().hashCode();
        result = 31 * result + price.hashCode();
        return result;
    }
}
