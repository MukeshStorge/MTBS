package com.moviebooking.repositories;

import java.util.Map;

import com.moviebooking.models.BookingRequestRedis;

public interface BookingRequestRepository 
{
	
	BookingRequestRedis save(BookingRequestRedis booking);

	BookingRequestRedis findById(Long timestamp);

	Map<Long, BookingRequestRedis> findAll();

}
