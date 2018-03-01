package com.colur;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Color {

    @Id
    @GeneratedValue
    private Long id;

    @JsonIgnore
    @ManyToOne
    private User user;

    private String color;

    private String date;

    private Color() { } // JPA only

    public Color(final User user, final String color, final String date) {
        this.color = color;
        this.date = date;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getColor() {
        return color;
    }

    public String getDate() {
        return date;
    }
}