package com.scholartrack.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String province;

    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String sector;

    @Column(nullable = false)
    private String cell;

    @Column(nullable = false)
    private String village;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<Student> students;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getProvince() { return province; }
    public void setProvince(String province) { this.province = province; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getSector() { return sector; }
    public void setSector(String sector) { this.sector = sector; }
    public String getCell() { return cell; }
    public void setCell(String cell) { this.cell = cell; }
    public String getVillage() { return village; }
    public void setVillage(String village) { this.village = village; }
    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }
}
