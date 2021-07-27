package com.moviebooking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebooking.models.SeatAvailability;
import com.moviebooking.service.SeatService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/seats")
@Tag(name = "List Seats for a Show")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/show/{showId}")
    public List<SeatAvailability> getSeatAvailability(@PathVariable int showId) {
        return seatService.getSeatAvailability(showId);
    }

}
