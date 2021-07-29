package com.mtbs.shows;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mtbs.models.ShowsSeat;

public interface ShowSeatRepository extends CrudRepository<ShowsSeat, Integer> {

    List<ShowsSeat> findAllByShowId(int showId);

}
