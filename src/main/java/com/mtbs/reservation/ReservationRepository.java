package com.mtbs.reservation;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mtbs.models.BookedSeat;
import com.mtbs.models.ShowsSeat;

public interface ReservationRepository extends CrudRepository<BookedSeat, Integer> {

    int countByUserIdAndShowsSeatIn(int userId, List<ShowsSeat> showsSeats);

}
