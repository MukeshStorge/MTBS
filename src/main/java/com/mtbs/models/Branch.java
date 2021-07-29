package com.mtbs.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	private String name;
	
	private String contactNumber;

	private String address;

	private String pinCode;
	
	@JsonBackReference(value = "branches")
	@ManyToOne
	@MapsId("theaterId")
	@JoinColumn(name = "theater_id", nullable = false, insertable = false, updatable = false)
	private Theater theater;
	
	@JsonManagedReference(value = "branch")
	@OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Screens> screens; 

}
