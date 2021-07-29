package com.mtbs.seats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtbs.models.SeatAvailability;

@Service
public class SeatService {

//	@Autowired
//	private Seatre
	
    @Autowired
    private SeatAvailabilityRepository seatAvailabilityRepository;

    public List<SeatAvailability> getSeatAvailability(int showId) {
        return seatAvailabilityRepository.findAllByShowId(showId);
    }

}
