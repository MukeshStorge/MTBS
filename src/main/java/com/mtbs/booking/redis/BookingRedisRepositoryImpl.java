package com.mtbs.booking.redis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.mtbs.models.BookingRedisParent;

@Repository
@SuppressWarnings(value = { "unchecked","rawtypes"})
public class BookingRedisRepositoryImpl implements BookingRedisRepository {

	@Value("${spring.redis.bucket}")
	private String bucketName; 
	
	@SuppressWarnings("unused")
	private RedisTemplate<String, BookingRedisParent> redisTemplate;
    private HashOperations hashOperations; 
    
    public BookingRedisRepositoryImpl(RedisTemplate<String, BookingRedisParent> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }
	
	@Override
    public BookingRedisParent save(BookingRedisParent booking) {
		hashOperations.put(bucketName,booking.getId(),booking);
        return booking; 
    }
	@Override
    public BookingRedisParent findById(String id) {
        return (BookingRedisParent)hashOperations.get(bucketName,id);
    }
	@Override
    public Map<String,BookingRedisParent> findAll() {
    	return hashOperations.entries(bucketName);
    }
}
