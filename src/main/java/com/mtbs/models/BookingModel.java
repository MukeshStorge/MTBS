package com.mtbs.models;

import java.util.Set;

import lombok.Data;

@Data
public class BookingModel {

	private int theaterId;
	
	private int branchId;
	
	private int screenId;
	
    private Set<Integer> seatIds;
 
    private User user;
}
