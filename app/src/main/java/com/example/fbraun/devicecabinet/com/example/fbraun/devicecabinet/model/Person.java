package com.example.fbraun.devicecabinet.com.example.fbraun.devicecabinet.model;

/**
 * Created by fbraun on 03.02.15.
 */
public class Person {
    public String firstName;
    public String lastName;
    private String fullName;
    public String personId;

    public String getFullName () {
        this.fullName = firstName + " " + lastName;
        return this.fullName;
    }
}
