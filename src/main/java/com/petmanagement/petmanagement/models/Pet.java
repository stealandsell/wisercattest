package com.petmanagement.petmanagement.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Entity
@Table(name = "PETS")
public class Pet {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 255, message = "Name must be between 1 and 255 characters")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "Code cannot be null")
    @Size(min = 1, max = 255, message = "Code must be between 1 and 50 characters")
    @Column(nullable = false)
    private String code;
    @NotNull(message = "Type cannot be null")
    @Size(min = 1, max = 255, message = "Type must be between 1 and 50 characters")
    @Column(nullable = false)
    private String type;
    @NotNull(message = "Fur color cannot be null")
    @Size(min = 1, max = 255, message = "Fur color must be between 1 and 50 characters")
    @Column(name = "fur_color", nullable = false)
    private String fur_color;
    @Size(max = 255, message = "Country of origin cannot be longer than 50 characters")
    @Column(name = "country_of_origin")
    private String country_of_origin;
    @Column(name = "owner_id")
    private Integer ownerId;

    public Pet() {
    }


    public Pet(String name, String code, String type, String fur_color, String country_of_origin, Integer ownerId) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.fur_color = fur_color;
        this.country_of_origin = country_of_origin;
        this.ownerId = ownerId;
    }


    // Getters and setters

    public String getCountryOfOrigin() {
        return country_of_origin;
    }

    public void setCountryOfOrigin(String country_of_origin) {
        this.country_of_origin = country_of_origin;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFurColor() {
        return fur_color;
    }

    public void setFurColor(String fur_color) {
        this.fur_color = fur_color;
    }

}