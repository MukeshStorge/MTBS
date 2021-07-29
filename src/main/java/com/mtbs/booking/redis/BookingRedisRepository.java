package com.mtbs.booking.redis;

import java.util.Map;

import com.mtbs.models.BookingRedisParent;

public interface BookingRedisRepository 
{
	
	BookingRedisParent save(BookingRedisParent booking);

	BookingRedisParent findById(Long bucketId);

	Map<Long, BookingRedisParent> findAll();

}
