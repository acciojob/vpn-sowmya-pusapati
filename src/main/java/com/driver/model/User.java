package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    private String password;

    private String originalIp;
    private String maskedIp;
    private boolean connected;

    public User() {
    }

    public User(int id, String userName, String password,  String originalIp, String maskedIp, boolean isConnected) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.originalIp = originalIp;
        this.maskedIp = maskedIp;
        this.connected = isConnected;
    }

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Country originalCountry;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    private List<Connection> connectionList=new ArrayList<>();

    @ManyToMany
    private List<ServiceProvider> serviceProviderList=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getOriginalIp() {
        return originalIp;
    }

    public void setOriginalIp(String originalIp) {
        this.originalIp = originalIp;
    }

    public String getMaskedIp() {
        return maskedIp;
    }

    public void setMaskedIp(String maskedIp) {
        this.maskedIp = maskedIp;
    }

    public boolean getConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        connected = connected;
    }

    public Country getOriginalCountry() {
        return originalCountry;
    }

    public void setOriginalCountry(Country country) {
        this.originalCountry= country;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public List<ServiceProvider> getServiceProviderList() {
        return serviceProviderList;
    }

    public void setServiceProviderList(List<ServiceProvider> serviceProviderList) {
        this.serviceProviderList = serviceProviderList;
    }
}
