package com.moviebooking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.moviebooking.exceptionhandler.ErrorResponseCodes;
import com.moviebooking.models.BlockedSeat;
import com.moviebooking.models.BookedSeat;
import com.moviebooking.models.BookingModel;
import com.moviebooking.models.ShowSeat;
import com.moviebooking.models.User;
import com.moviebooking.paymentgateway.PaymentGatewayCall;
import com.moviebooking.repositories.BlockedSeatRepository;
import com.moviebooking.repositories.BookedSeatRepository;

@Service
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BlockedSeatRepository blockedSeatRepository;

    @Autowired
    private BookedSeatRepository bookedSeatRepository;

    @Autowired
    private PaymentGatewayCall paymentGatewayCall;

    public ErrorResponseCodes bookSeats(BookingModel bookingRequest) {
        List<BlockedSeat> seatsToBlock = null;
        try {
            int userId = 1;
            seatsToBlock = bookingRequest.getShowSeatIds().stream().map(id -> {
                BlockedSeat blockedSeat = new BlockedSeat(new User(userId), new ShowSeat(id));
                return blockedSeat;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error getting blocked seats", e);
            return ErrorResponseCodes.SERVER_ERROR;
        }
//        ??
        blockSeats(seatsToBlock);
        double amount = seatsToBlock.stream().mapToDouble(s -> s.getShowSeat().getPrice()).sum();
        return paymentGatewayCall.payment(amount) ? moveToBooked(seatsToBlock) : ErrorResponseCodes.PAYMENT_FAILED;
    }

//    ??
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    private String blockSeats(List<BlockedSeat> blockedSeats) {
        try {
            blockedSeatRepository.saveAll(blockedSeats);
            return null;
        } catch (DataIntegrityViolationException die) {
            logger.error("Error inserting to blocked seats", die);
            return "Requested seats are not available";
        } catch (Exception e) {
            logger.error("Error inserting to blocked seats", e);
            return "Error blocking seats";
        }
    }

//    ??
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    private ErrorResponseCodes moveToBooked(List<BlockedSeat> blockedSeats) {
        try {
            bookedSeatRepository.saveAll(blockedSeats.stream().map(b -> {
                return new BookedSeat(b.getUser(), b.getShowSeat());
            }).collect(Collectors.toList()));
            return ErrorResponseCodes.SUCCESS;
        } catch (DataIntegrityViolationException die) {
            logger.error(ErrorResponseCodes.SEATS_NA.getErrorMessage(), die);
            return ErrorResponseCodes.SEATS_NA;
        } catch (Exception e) {
            logger.error(ErrorResponseCodes.SERVER_ERROR.getErrorMessage(), e);
            return ErrorResponseCodes.SERVER_ERROR;
        } finally {
            blockedSeatRepository.deleteAll(blockedSeats);
        }
    }

}
