package com.mtbs.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Movies {

    @Id
    private int id;

    private String name;

    private int runningTimeHour;

    private String language;

}
