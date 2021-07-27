package com.moviebooking.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.moviebooking.models.SeatAvailability;

public interface SeatAvailabilityRepository extends CrudRepository<SeatAvailability, Integer> {
    
    List<SeatAvailability> findAllByShowId(int showId);

    int countByIdIn(Set<Integer> showSeatIds);

}
