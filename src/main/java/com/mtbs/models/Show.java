package com.mtbs.models;

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
    private Movies movies;

    @OneToOne
    @JoinColumn(name = "screens_id")
    private Screens screens;

    private Date startTime;

    private Date endTime;

}
