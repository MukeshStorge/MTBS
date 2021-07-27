package com.moviebooking.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import lombok.Data;

@Data
public class BookingRequestRedis implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4529395733586171543L;

    Long timeStamp = new Timestamp(System.currentTimeMillis()).getTime();
    
    List<BookingRequest>bookingRequest=null;
}
