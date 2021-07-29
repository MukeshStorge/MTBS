package com.mtbs.validator;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mtbs.booking.redis.BookingRedisService;
import com.mtbs.exceptionhandler.ErrorResponseCodes;
import com.mtbs.models.ShowsSeat;
import com.mtbs.reservation.BlockedSeatRepository;
import com.mtbs.reservation.ReservationRepository;
import com.mtbs.shows.ShowSeatRepository;

@Component
public class Validator {

	@Value("${app.booking.maxSeatsAllowed}")
	private int maxSeatsPerUser;

	@Autowired
	private BlockedSeatRepository blockedSeatRepository;

	@Autowired
	private ReservationRepository reservationRepository;

	@Autowired
	private ShowSeatRepository showSeatRepository;

	private static final Logger logger = LoggerFactory.getLogger(BookingRedisService.class);

	public ErrorResponseCodes validate(Set<Integer> showSeatIds, int userId) {
		try {
			if (showSeatIds.size() > maxSeatsPerUser) {
				return ErrorResponseCodes.MAX_SEATS_SHOW;
			}

			List<ShowsSeat> showSeatsSelected = (List<ShowsSeat>) showSeatRepository.findAllById(showSeatIds);

			/**
			 * allSeatsBelongToSameShow
			 */
			if (!(showSeatsSelected.stream().map(s -> s.getShow().getId()).distinct().count() == 1)) {
				return ErrorResponseCodes.INVALID_SEAT;
			}

			if (blockedSeatRepository.countByUserId(userId) > 0) {
				return ErrorResponseCodes.DUPLICATE_SESSION;
			}

			int showId = showSeatsSelected.get(0).getShow().getId();
			List<ShowsSeat> allShowSeats = showSeatRepository.findAllByShowId(showId);
			int prevBookedSeats = reservationRepository.countByUserIdAndShowsSeatIn(userId, allShowSeats);

			if (prevBookedSeats + showSeatIds.size() > maxSeatsPerUser) {
				return ErrorResponseCodes.MAX_SEATS_SHOW;
			}

		} catch (Exception e) {
			logger.error(ErrorResponseCodes.SERVER_ERROR.toString(), e);
			return ErrorResponseCodes.SERVER_ERROR;
		}
		return null;
	}
}
