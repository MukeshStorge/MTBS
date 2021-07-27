package com.moviebooking.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.moviebooking.models.ShowSeat;

public interface ShowSeatRepository extends CrudRepository<ShowSeat, Integer> {

    List<ShowSeat> findAllByShowId(int showId);

}
