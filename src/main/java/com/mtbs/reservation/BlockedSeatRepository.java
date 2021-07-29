package com.mtbs.reservation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mtbs.models.BlockedSeat;

public interface BlockedSeatRepository extends CrudRepository<BlockedSeat, Integer> {

    int countByUserId(int userId);

    @Query(nativeQuery = true, value = "select mtbs.remove_timedout_records()")
    void releaseTimedoutBlockedSeats();

}
