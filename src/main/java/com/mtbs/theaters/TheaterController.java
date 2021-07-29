package com.mtbs.theaters;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mtbs.models.Theater;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/theaters")
@Tag(name = "Master - Theaters")
public class TheaterController {

    @Autowired
    private TheaterRepository theaterRepository;

    @GetMapping("/all")
    public List<Theater> showAllTheaters() {
        return (List<Theater>) theaterRepository.findAll();
    }

    @GetMapping("/{theaterId}")
    public Optional<Theater> showTheaterById(@RequestParam(required = true) Integer theaterId) {
        return theaterRepository.findById(theaterId);
    }

    @PostMapping("/add")
    public ResponseEntity<Theater> showAllTheaters(@RequestBody Theater theater) {
			return ResponseEntity.status(HttpStatus.CREATED).body(theaterRepository.save(theater));
    }

    @DeleteMapping("/delete/{theaterId}")
    public void deleteTheaterById(@RequestParam(required = true) Integer theaterId) {
         theaterRepository.deleteById(theaterId);
    }

}
