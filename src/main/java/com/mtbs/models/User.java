package com.mtbs.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

    public User(int userId) {
        this.id = userId;
    }

    public User(int userId, String firstName) {
        this.id = userId;
        this.firstName = firstName;
    }

    @Id
    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private boolean activeStatus;

}
