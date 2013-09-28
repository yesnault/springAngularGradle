package com.yesnault.sag.model;

import org.springframework.util.Assert;

import javax.persistence.Entity;

@Entity
public class User extends AbstractEntity {

    private String firstname, lastname;

    public User(String firstname, String lastname) {

        Assert.hasText(firstname);
        Assert.hasText(lastname);

        this.firstname = firstname;
        this.lastname = lastname;
    }

    protected User() {

    }


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, firstname='%s', lastname='%s']",
                getId(), firstname, lastname);
    }
}
