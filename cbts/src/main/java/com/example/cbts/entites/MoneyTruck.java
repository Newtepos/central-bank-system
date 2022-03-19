package com.example.cbts.entites;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
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
}
