package com.moviebooking.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class ShowSeat {

    public ShowSeat(int showSeatId) {
        this.id = showSeatId;
    }

    @Id
    private int id;

    @OneToOne
    @JoinColumn(name = "show_id")
    private Show show;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private double price;

}
