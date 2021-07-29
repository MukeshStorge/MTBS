package com.mtbs.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class BookingRedisParent implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4529395733586171543L;

    Long timeStamp = new Timestamp(System.currentTimeMillis()).getTime();
//	Long timeStamp =1234L;
    List<BookingRedisChild>bookingRedisChild=new ArrayList<>();
}
