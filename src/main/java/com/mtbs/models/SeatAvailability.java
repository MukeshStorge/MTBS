package com.mtbs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class SeatAvailability {

    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "show_id")
    @JsonIgnore
    private Show show;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private double price;

    private boolean available;

}
