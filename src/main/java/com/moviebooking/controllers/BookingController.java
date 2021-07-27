package com.moviebooking.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebooking.exceptionhandler.ErrorResponseCodes;
import com.moviebooking.models.BookingModel;
import com.moviebooking.models.BookingRequestRedis;
import com.moviebooking.service.BookingRequestService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/booking")
@Tag(name = "Reserve Seats")
public class BookingController {

	@Value("${app.booking.maxSeatsAllowed}")
	private Integer maxSeatsAllowed;

	private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private BookingRequestService bookingRequestService;

	@PostMapping("/reserve")
	public ResponseEntity<?> bookSeats(@RequestBody BookingModel bookingModel) {
		try {
			if (bookingModel != null && bookingModel.getShowSeatIds().size() < maxSeatsAllowed)
				return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseCodes.MAX_SEATS);

			BookingRequestRedis requestMessage = new BookingRequestRedis();
			String correlationId = UUID.randomUUID().toString();
			requestMessage = bookingRequestService.pushToRedis(bookingModel, requestMessage, correlationId);
			if (bookingRequestService.filterDisjointSet(requestMessage, correlationId)) {
				return bookingRequestService.process(bookingModel);
			} else {
				return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponseCodes.BLOCKED_SEAT);
			}

		} catch (Exception e) {
			logger.error(ErrorResponseCodes.SERVER_ERROR.getErrorMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponseCodes.SERVER_ERROR);
		} finally {

		}
	}

}
