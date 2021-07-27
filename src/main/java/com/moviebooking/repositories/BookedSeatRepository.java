package com.moviebooking.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.moviebooking.models.BookedSeat;
import com.moviebooking.models.ShowSeat;

public interface BookedSeatRepository extends CrudRepository<BookedSeat, Integer> {

    int countByUserIdAndShowSeatIn(int userId, List<ShowSeat> showSeats);

}
