package com.mtbs.models;

import java.io.IOException;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Entity
@Data
public class BookingRedisChild implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4504074177325982841L;

	static ObjectMapper objectMapper = new ObjectMapper();
	 
	@Id
    private String correlationId;
	
    private String data;

    @Column(insertable = false, updatable = false)
    private long requestTimeMillisec;

    private boolean processed;

    @JsonIgnore
    public BookingModel getBookingData() {
        BookingModel myPojo = null;
        try {
            myPojo = objectMapper.readValue(getData(), BookingModel.class);
        } catch (JsonParseException e) {
        } catch (JsonMappingException e) {
        } catch (IOException e) {
        }
        return myPojo;
    }
}
