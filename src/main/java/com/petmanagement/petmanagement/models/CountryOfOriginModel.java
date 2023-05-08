package com.petmanagement.petmanagement.models;
import jakarta.persistence.*;

@Entity
@Table(name = "country_of_origin")
public class CountryOfOriginModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;


    // getters setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
