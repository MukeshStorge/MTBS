package com.mtbs.seats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtbs.models.SeatAvailability;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/seat")
@Tag(name = "Master - Seat")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping("/show/{showId}")
    public List<SeatAvailability> getSeatAvailability(@PathVariable int showId) {
        return seatService.getSeatAvailability(showId);
    }

}
