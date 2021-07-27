package com.moviebooking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebooking.models.Show;
import com.moviebooking.repositories.ShowRepository;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public List<Show> listAllShows() {
        return (List<Show>) showRepository.findAll();
    }

    public List<Show> listAllShowsByMovieOrHall(String movieName, String movieHallName) {
        return showRepository.findAllShowById(movieName, movieHallName);
    }

    public Optional<Show> findById(int showId) {
        return showRepository.findById(showId);
    }

}
