package com.frama.miserend.hu.search;

import java.io.Serializable;

/**
 * Created by Balazs on 2018. 03. 22..
 */

public class SearchParams implements Serializable {
    private String searchTerm;
    private String city;

    public SearchParams() {
        super();
    }

    public SearchParams(String searchTerm) {
        super();
        this.searchTerm = searchTerm;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
