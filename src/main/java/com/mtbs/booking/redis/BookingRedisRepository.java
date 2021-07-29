package com.mtbs.booking.redis;

import java.util.Map;

import com.mtbs.models.BookingRedisParent;

public interface BookingRedisRepository 
{
	
	BookingRedisParent save(BookingRedisParent booking);

	BookingRedisParent findById(String id);

	Map<String, BookingRedisParent> findAll();

}
