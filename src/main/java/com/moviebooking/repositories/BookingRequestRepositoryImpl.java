package com.moviebooking.repositories;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Repository;

import com.moviebooking.models.BookingRequestRedis;

@Repository
@SuppressWarnings(value = { "unchecked","rawtypes"})
public class BookingRequestRepositoryImpl implements BookingRequestRepository {

	@Value("${spring.redis.bucket}")
	private String bucketName; 
	
	private HashOperations hashOperations;

	@Override
    public BookingRequestRedis save(BookingRequestRedis booking) {
		hashOperations.put(bucketName,booking.getTimeStamp(),booking);
        return booking; 
    }
	@Override
    public BookingRequestRedis findById(Long timestamp) {
        return (BookingRequestRedis)hashOperations.get(bucketName,timestamp);
    }
	@Override
    public Map<Long,BookingRequestRedis> findAll() {
    	return hashOperations.entries(bucketName);
    }
}
