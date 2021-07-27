package com.moviebooking.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.moviebooking.models.Show;

public interface ShowRepository extends CrudRepository<Show, Integer> {

    @Query("SELECT s from Show s WHERE (:movieName is null or s.movie.name LIKE %:movieName%) and (:movieHallName is null or s.movieHall.name LIKE %:movieHallName%)")
    List<Show> findAllShowById(String movieName, String movieHallName);

}
