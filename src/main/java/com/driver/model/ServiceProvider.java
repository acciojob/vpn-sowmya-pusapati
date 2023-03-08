package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ServiceProvider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    public ServiceProvider() {
    }

    public ServiceProvider(int id, String name) {
        this.id = id;
        this.name = name;
    }
    @ManyToOne
    @JoinColumn
    private Admin admin;

    @ManyToMany
    private List<User> users=new ArrayList<>();

    @OneToMany(mappedBy = "serviceProvider",cascade = CascadeType.ALL)
    private List<Connection> connectionList=new ArrayList<>();

    @OneToMany(mappedBy = "serviceProvider",cascade = CascadeType.ALL)
    private List<Country> countryList=new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> userList) {
        this.users = userList;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<Connection> connectionList) {
        this.connectionList = connectionList;
    }

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }
}
