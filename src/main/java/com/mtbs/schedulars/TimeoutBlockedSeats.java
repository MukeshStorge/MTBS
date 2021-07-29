package com.mtbs.schedulars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mtbs.reservation.BlockedSeatRepository;

@Component
public class TimeoutBlockedSeats {

    private static final Logger logger = LoggerFactory.getLogger(TimeoutBlockedSeats.class);

    @Autowired
    private BlockedSeatRepository blockedSeatRepository;

    @Scheduled(fixedDelay = 500)
    void releaseTimedOutSeats() {
        try {
            blockedSeatRepository.releaseTimedoutBlockedSeats();
        } catch (Exception e) {
            logger.error("Error releasing seats", e);
        }
    }

}
