package com.moviebooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebooking.models.SeatAvailability;
import com.moviebooking.repositories.SeatAvailabilityRepository;

@Service
public class SeatService {

    @Autowired
    private SeatAvailabilityRepository seatAvailabilityRepository;

    public List<SeatAvailability> getSeatAvailability(int showId) {
        return seatAvailabilityRepository.findAllByShowId(showId);
    }

}
