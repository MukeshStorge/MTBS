package com.mtbs.reservation;

import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtbs.booking.redis.BookingRedisService;
import com.mtbs.exceptionhandler.ErrorResponseCodes;
import com.mtbs.models.BookingModel;
import com.mtbs.models.BookingRedisParent;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/reserve")
@Tag(name = "Transaction - Reserve Seats")
public class ReservationController {

	@Value("${app.booking.maxSeatsAllowed}")
	private Integer maxSeatsAllowed;

	private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

	@Autowired
	private BookingRedisService bookingRedisService;
	
	@Autowired
	private ReservationService reservationService;

	@PostMapping("/book")
	public ResponseEntity<?> bookSeats(@RequestBody BookingModel bookingModel) {
		try {
			if (bookingModel != null && bookingModel.getSeatIds().size() > maxSeatsAllowed)
				return ResponseEntity.badRequest().body(ErrorResponseCodes.MAX_SEATS.toString());

			BookingRedisParent requestMessage = new BookingRedisParent();
			String correlationId = UUID.randomUUID().toString();
			requestMessage = bookingRedisService.pushToRedis(bookingModel, requestMessage, correlationId);

			return reservationService.filterDisjointSet(requestMessage, correlationId)
					? reservationService.processor(bookingModel)
					: ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseCodes.BLOCKED_SEAT.toString());
			
		} catch (Exception e) {
			logger.error(ErrorResponseCodes.SERVER_ERROR.toString(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(ErrorResponseCodes.SERVER_ERROR.toString());
		}
	}

	@GetMapping
	public Map<Long, BookingRedisParent> findAll() {
		return bookingRedisService.findAll();
	}

}
