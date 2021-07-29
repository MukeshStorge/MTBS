package com.mtbs.reservation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mtbs.exceptionhandler.ErrorResponseCodes;
import com.mtbs.models.BlockedSeat;
import com.mtbs.models.BookedSeat;
import com.mtbs.models.BookingModel;
import com.mtbs.models.BookingRedisChild;
import com.mtbs.models.BookingRedisParent;
import com.mtbs.models.ShowsSeat;
import com.mtbs.models.Ticket;
import com.mtbs.models.User;
import com.mtbs.paymentgateway.PaymentGatewayCall;
import com.mtbs.validator.Validator;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private BlockedSeatRepository blockedSeatRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private PaymentGatewayCall paymentGatewayCall;
    
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private Validator validator;

    public ErrorResponseCodes bookSeats(BookingModel bookingRequest) {
        List<BlockedSeat> seatsToBlock = null;
        try {
            int userId = bookingRequest.getUser().getId();
            seatsToBlock = bookingRequest.getSeatIds().stream().map(id -> {
                BlockedSeat blockedSeat = new BlockedSeat(new User(userId), new ShowsSeat(id));
                return blockedSeat;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error( ErrorResponseCodes.SERVER_ERROR.toString(), e);
            return ErrorResponseCodes.SERVER_ERROR;
        }
        blockSeats(seatsToBlock);
        double amount = seatsToBlock.stream().mapToDouble(s -> s.getShowsSeat().getPrice()).sum();
        return paymentGatewayCall.payment(amount) ? moveToBooked(seatsToBlock) : ErrorResponseCodes.PAYMENT_FAILED;
    }

	public Boolean filterDisjointSet(BookingRedisParent requestMessage, String correlationId)
			throws JsonMappingException, JsonProcessingException {
		BookingRedisParent bookingRedisParent = new BookingRedisParent();
		unionFindHandler(new ArrayList<>(), 0, requestMessage.getBookingRedisChild(), bookingRedisParent);
		for (BookingRedisChild bookingRedisChild : bookingRedisParent.getBookingRedisChild()) {
			if (correlationId.equals(bookingRedisChild.getCorrelationId())) 
				return true;
		}
		return false;
	}
	
	public void unionFindHandler(List<BookingRedisChild> bookingRequestList, int idx, List<BookingRedisChild> bookingRedisChild,
			BookingRedisParent bookingRedisParent) {
		if (idx == bookingRedisChild.size()) {
			int size = 0;
			for (BookingRedisChild bk : bookingRequestList) 
				size += bk.getBookingData().getSeatIds().size();
			int maxSize = 0;
			for (BookingRedisChild bk : bookingRedisParent.getBookingRedisChild()) 
				maxSize += bk.getBookingData().getSeatIds().size();
			if (size > maxSize) {
				maxSize = size;
				bookingRedisParent.setBookingRedisChild(new ArrayList<>(bookingRequestList));
			}
			return;
		}
		if (!isDisjoint(bookingRequestList, idx, bookingRedisChild)) {
			bookingRequestList.add(bookingRedisChild.get(idx));
			unionFindHandler(bookingRequestList, idx + 1, bookingRedisChild, bookingRedisParent);
			bookingRequestList.remove(bookingRequestList.size() - 1);
		}
		unionFindHandler(bookingRequestList, idx + 1, bookingRedisChild, bookingRedisParent);
	}

	public boolean isDisjoint(List<BookingRedisChild> li, int idx, List<BookingRedisChild> bookingRedisChild) {
		for (BookingRedisChild booking : li) {
			if (!Collections.disjoint(booking.getBookingData().getSeatIds(),
					bookingRedisChild.get(idx).getBookingData().getSeatIds())) {
				return true;
			}
		}
		return false;
	}


    public ResponseEntity<?> processor(BookingModel bookingModel) {
		bookingModel.setUser(bookingModel.getUser());
		ErrorResponseCodes errorResponse = validator.validate(bookingModel.getSeatIds(), bookingModel.getUser().getId());
		if (errorResponse!=null&&errorResponse.toString() != null) {
			return ResponseEntity.badRequest().body(errorResponse.toString());
		}

		errorResponse = reservationService.bookSeats(bookingModel);
		return "Success".equals(errorResponse.getErrorMessage())
				? ResponseEntity.ok().body(new Ticket(bookingModel.getSeatIds()))
				: ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse.toString());
	}


    
    @Transactional(propagation = Propagation.REQUIRED)
    private String blockSeats(List<BlockedSeat> blockedSeats) {
        try {
            blockedSeatRepository.saveAll(blockedSeats);
            return null;
        } catch (DataIntegrityViolationException die) {
            logger.error(ErrorResponseCodes.SEATS_NA.toString(), die);
            return ErrorResponseCodes.SEATS_NA.toString();
        } catch (Exception e) {
            logger.error(ErrorResponseCodes.ERROR_BLOCKING_SEAT.toString(), e);
            return ErrorResponseCodes.ERROR_BLOCKING_SEAT.toString();
        }
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    private ErrorResponseCodes moveToBooked(List<BlockedSeat> blockedSeats) {
        try {
            reservationRepository.saveAll(blockedSeats.stream().map(b -> {
                return new BookedSeat(b.getUser(), b.getShowsSeat());
            }).collect(Collectors.toList()));
            return ErrorResponseCodes.SUCCESS;
        } catch (DataIntegrityViolationException die) {
            logger.error(ErrorResponseCodes.SEATS_NA.toString(), die);
            return ErrorResponseCodes.SEATS_NA;
        } catch (Exception e) {
            logger.error(ErrorResponseCodes.SERVER_ERROR.toString(), e);
            return ErrorResponseCodes.SERVER_ERROR;
        } finally {
            blockedSeatRepository.deleteAll(blockedSeats);
        }
    }

}
