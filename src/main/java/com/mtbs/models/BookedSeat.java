package com.mtbs.models;

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
public class BookedSeat {

    public BookedSeat(User user, ShowsSeat showsSeat) {
        this.user = user;
        this.showsSeat = showsSeat;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "show_seat_id")
    private ShowsSeat showsSeat;

    @Column(insertable = false, updatable = false)
    private Date bookedTime;

}
