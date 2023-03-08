package com.driver.model;

import javax.persistence.*;

// Note: Do not write @Enumerated annotation above CountryName in this model.
@Entity
public class Country{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;
    private CountryName countryName;
    private String code;

    public Country() {
    }

    public Country(int countryId, CountryName countryName, String code) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.code =countryName.toCode();
    }

    @OneToOne
    private User user;

    @ManyToOne
    @JoinColumn
    private ServiceProvider serviceProvider;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public CountryName getCountryName() {
        return countryName;
    }

    public void setCountryName(CountryName countryName) {
        this.countryName = countryName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String countryCode) {
        this.code = countryCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}