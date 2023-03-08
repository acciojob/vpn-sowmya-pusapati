package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    String username;
    String password;

    public Admin() {
    }

    public Admin(int id, String adminName, String password) {
        this.id = id;
        this.username = adminName;
        this.password = password;
    }
    @OneToMany(mappedBy = "admin",cascade = CascadeType.ALL)
    List<ServiceProvider> serviceProviders=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String adminName) {
        this.username = adminName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }

    public void setServiceProviders(List<ServiceProvider> serviceProviders) {
        this.serviceProviders = serviceProviders;
    }
}

