package com.scholartrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "locations",
        indexes = {
                @Index(name = "idx_locations_type", columnList = "type"),
                @Index(name = "idx_locations_parent", columnList = "parent_id")
        })
public class Location {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false, columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ELocationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Location parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<Location> children = new ArrayList<>();

    public Location() {
    }

    public Location(String name, String code, ELocationType type, Location parent) {
        this.name = name;
        this.code = code;
        this.type = type;
        this.parent = parent;
    }

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

    public ELocationType getType() {
        return type;
    }

    public void setType(ELocationType type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Location getParent() {
        return parent;
    }

    public void setParent(Location parent) {
        this.parent = parent;
    }

    public List<Location> getChildren() {
        return children;
    }

    public void setChildren(List<Location> children) {
        this.children = children;
    }

    public void addChild(Location child) {
        Objects.requireNonNull(child, "child location is required");
        child.setParent(this);
        this.children.add(child);
    }
}
