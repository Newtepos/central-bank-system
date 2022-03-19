package com.example.cbts.entites;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
public class MoneyTruck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String truckName;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Location> locations;

    public MoneyTruck() {
    }

    public MoneyTruck(List<Location> locations) {
        this.locations = locations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public void addLocation(Location location) {
        this.locations.add(location);
    }

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }
}
