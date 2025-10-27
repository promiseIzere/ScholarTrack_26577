package com.scholartrack.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name="location")
@JsonIgnoreProperties({"parent"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location {
    @Id
    @GeneratedValue( strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="name")
    private String name;
    
    @Column(name="code")
    private String code;

    @Enumerated(EnumType.STRING)
    private ELocation type;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Location parent;

    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public ELocation getType() {
        return type;
    }
    public void setType(ELocation type) {
        this.type = type;
    }
    public Location getParent() {
        return parent;
    }
    public void setParent(Location parent) {
        this.parent = parent;
    }

    
}
