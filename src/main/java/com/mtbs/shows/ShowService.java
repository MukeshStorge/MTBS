package com.mtbs.shows;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mtbs.models.Show;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    public List<Show> listAllShows() {
        return (List<Show>) showRepository.findAll();
    }

    public Optional<Show> findById(int showId) {
        return showRepository.findById(showId);
    }

	public List<Show> listAllShowsByScreens(String movieName, String screen) {
		  return showRepository.findAllShowById(movieName, screen);
	}

}
