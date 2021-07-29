package com.mtbs.booking.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtbs.models.BookingModel;
import com.mtbs.models.BookingRedisChild;
import com.mtbs.models.BookingRedisParent;

@Service
public class BookingRedisService {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private BookingRedisRepository bookingRedisRepository;


	public Map<String, BookingRedisParent> findAll() {
		return bookingRedisRepository.findAll();
	}

	public BookingRedisParent pushToRedis(BookingModel bookingModel, BookingRedisParent requestMessage,
			String correlationId) throws JsonProcessingException {
		BookingRedisParent concurrentObj = bookingRedisRepository.findById(requestMessage.getId());
		List<BookingRedisChild> bookingRequestList = concurrentObj == null ? new ArrayList<BookingRedisChild>()
				: concurrentObj.getBookingRedisChild();
		BookingRedisChild request = new BookingRedisChild();
		request.setCorrelationId(correlationId);
		request.setData(objectMapper.writeValueAsString(bookingModel));
		bookingRequestList.add(request);
		requestMessage.setBookingRedisChild(bookingRequestList);
		return bookingRedisRepository.save(requestMessage);
	}
	

}
