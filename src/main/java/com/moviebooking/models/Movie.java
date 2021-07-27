package com.moviebooking.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Movie {

    @Id
    private int id;

    private String name;

    private int runningTimeHour;

    private String language;

}
