package com.mtbs.shows;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mtbs.models.Show;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/shows")
@Tag(name = "Master - Shows")
public class ShowController {

    @Autowired
    private ShowService showService;

    @GetMapping("/{movieName}/{screen}")
    public List<Show> listAllShowsByScreens(@RequestParam(required = false) String movieName,
            @RequestParam(required = false) String screen) {
        return showService.listAllShowsByScreens(movieName, screen);
    }

}
