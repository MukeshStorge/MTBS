package com.moviebooking.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class BlockedSeat {

    public BlockedSeat(User user, ShowSeat showSeat) {
        this.user = user;
        this.showSeat = showSeat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "show_seat_id")
    private ShowSeat showSeat;

    @Column(insertable = false, updatable = false)
    private Date blockedTime;

}
