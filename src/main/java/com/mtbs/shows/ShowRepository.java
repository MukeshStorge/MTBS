package com.mtbs.shows;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mtbs.models.Show;

public interface ShowRepository extends CrudRepository<Show, Integer> {

    @Query("SELECT s from Show s WHERE (:movieName is null or s.movies.name LIKE %:movieName%) and (:screen is null or s.screens.name LIKE %:screen%)")
    List<Show> findAllShowById(String movieName, String screen);

}
