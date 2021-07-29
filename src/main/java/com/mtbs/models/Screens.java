package com.mtbs.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
public class Screens {

    @Id
    private int id;

    private String name;
    
	@JsonBackReference(value = "branch")
	@ManyToOne
	@MapsId("branchId")
	@JoinColumn(name = "branch_id", nullable = false, insertable = false, updatable = false)
	private Branch branch;
	
	private Integer capacity;
	
	private String size;
	
	private String status;
}
