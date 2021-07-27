package com.moviebooking.models;

import java.util.Set;

import lombok.Data;

@Data
public class BookingModel {

    private Set<Integer> showSeatIds;
 
    private User user;
}
