package com.colur;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @OneToMany(mappedBy = "user")
    private Set<Color> colors = new HashSet<>();

    private User() { } 

    public User(final String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }


    public Set<Color> getColors() {
        return colors;
    }
}