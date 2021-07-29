package com.mtbs.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BookingRedisParent implements Serializable {

	private static final long serialVersionUID = -4529395733586171543L;

	public BookingRedisParent(){}
	/**
	 * Unique id for request for that millisecond
	 * @param bookingModel
	 */
	public BookingRedisParent(BookingModel bookingModel) { 
		this.id = bookingModel.getTheaterId() + bookingModel.getScreenId() + bookingModel.getBranchId()
				+ new Timestamp(System.currentTimeMillis()).getTime();
	}

	Long id;
	
	List<BookingRedisChild> bookingRedisChild = new ArrayList<>();
}
