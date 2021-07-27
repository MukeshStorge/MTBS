package com.moviebooking.repositories;

import org.springframework.data.repository.CrudRepository;

import com.moviebooking.models.BlockedSeat;

public interface BlockedSeatRepository extends CrudRepository<BlockedSeat, Integer> {

    int countByUserId(int userId);

}
