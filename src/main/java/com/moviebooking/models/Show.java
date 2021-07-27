package com.moviebooking.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Show {

    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToOne
    @JoinColumn(name = "movie_hall_id")
    private MovieHall movieHall;

    private Date startTime;

    private Date endTime;

}
