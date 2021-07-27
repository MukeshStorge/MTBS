package com.moviebooking.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviebooking.exceptionhandler.ErrorResponseCodes;
import com.moviebooking.models.BookingModel;
import com.moviebooking.models.BookingRequest;
import com.moviebooking.models.BookingRequestRedis;
import com.moviebooking.models.ShowSeat;
import com.moviebooking.models.Ticket;
import com.moviebooking.repositories.BlockedSeatRepository;
import com.moviebooking.repositories.BookedSeatRepository;
import com.moviebooking.repositories.BookingRequestRepository;
import com.moviebooking.repositories.ShowSeatRepository;

@Service
public class BookingRequestService {

	private static final Logger logger = LoggerFactory.getLogger(BookingRequestService.class);

	@Value("${app.booking.maxSeatsAllowed}")
	private int maxSeatsPerUser;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private BookingRequestRepository bookingRequestRepository;

	@Autowired
	private BlockedSeatRepository blockedSeatRepository;

	@Autowired
	private BookedSeatRepository bookedSeatRepository;

	@Autowired
	private ShowSeatRepository showSeatRepository;

	@Autowired
	private BookingService bookingService;

	public Map<Long, BookingRequestRedis> findAll() {
		return bookingRequestRepository.findAll();
	}


	public BookingRequestRedis pushToRedis(BookingModel bookingModel, BookingRequestRedis requestMessage, String correlationId)
			throws JsonProcessingException {
		BookingRequestRedis concurrentObj = bookingRequestRepository.findById(requestMessage.getTimeStamp());
		List<BookingRequest> bookingRequestList = concurrentObj == null ? new ArrayList<BookingRequest>()
				: concurrentObj.getBookingRequest();
		BookingRequest request = new BookingRequest();
		request.setCorrelationId(correlationId);
		request.setData(objectMapper.writeValueAsString(bookingModel));
		bookingRequestList.add(request);
		requestMessage.setBookingRequest(bookingRequestList);
		return bookingRequestRepository.save(requestMessage);
	}

	public Boolean filterDisjointSet(BookingRequestRedis requestMessage, String correlationId)
			throws JsonMappingException, JsonProcessingException {
		List<BookingRequest> bookingRequestList = requestMessage.getBookingRequest();
		for (int i = 0; i < bookingRequestList.size(); i++) {
			BookingRequest first = bookingRequestList.get(i);
			BookingRequest next = null;
			for (int j = i; j < bookingRequestList.size(); j++) {
				if (i != j) {
					next = bookingRequestList.get(j);
					if (!Collections.disjoint(first.getBookingData().getShowSeatIds(),
							next.getBookingData().getShowSeatIds())) 
						if (first.getBookingData().getShowSeatIds().size() <= next.getBookingData().getShowSeatIds()
								.size()) 
							first = next;
				}
			}
			if (first.getCorrelationId().equals(correlationId))
				return true;
		}
		return false;
	}

	public ResponseEntity<?> process(BookingModel bookingModel) {
		bookingModel.setUser(bookingModel.getUser());
		ErrorResponseCodes errorResponse = validator(bookingModel.getShowSeatIds(), bookingModel.getUser().getId());
		if (errorResponse.getErrorMessage() != null) {
			return ResponseEntity.badRequest().body(errorResponse);
		}

		errorResponse = bookingService.bookSeats(bookingModel);
		return "ok".equals(errorResponse.getErrorMessage()) ? ResponseEntity.ok().body(new Ticket(bookingModel.getShowSeatIds()))
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}

	
	private ErrorResponseCodes validator(Set<Integer> showSeatIds, int userId) {
		try {
			if (showSeatIds.size() > maxSeatsPerUser) {
				return ErrorResponseCodes.MAX_SEATS_SHOW;
			}

			List<ShowSeat> showSeatsSelected = (List<ShowSeat>) showSeatRepository.findAllById(showSeatIds);
			
			boolean allSeatsBelongToSameShow = showSeatsSelected.stream().map(s -> s.getShow().getId()).distinct()
					.count() == 1;
			
			if (!allSeatsBelongToSameShow) {
				return ErrorResponseCodes.INVALID_SEAT;
			}

			if (blockedSeatRepository.countByUserId(userId) > 0) {
				return ErrorResponseCodes.DUPLICATE_SESSION;
			}
			
			int showId = showSeatsSelected.get(0).getShow().getId();
			List<ShowSeat> allShowSeats = showSeatRepository.findAllByShowId(showId);
			int prevBookedSeats = bookedSeatRepository.countByUserIdAndShowSeatIn(userId, allShowSeats);
			
			if (prevBookedSeats + showSeatIds.size() > maxSeatsPerUser) {
				return ErrorResponseCodes.MAX_SEATS_SHOW;
			}
			
		} catch (Exception e) {
			logger.error(ErrorResponseCodes.SERVER_ERROR.getErrorMessage(), e);
			return ErrorResponseCodes.SERVER_ERROR;
		}
		return null;
	}

}
