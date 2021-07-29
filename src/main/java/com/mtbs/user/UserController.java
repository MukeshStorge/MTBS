package com.mtbs.user;

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

import com.mtbs.models.User;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@Tag(name = "Master - User")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public List<User> showAllUser() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public Optional<User> showUserById(@RequestParam(required = true) Integer userId) {
        return userRepository.findById(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<User> showAllUser(@RequestBody User user) {
			return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    @DeleteMapping("/delete/{userId}")
    public void deleteUserById(@RequestParam(required = true) Integer userId) {
         userRepository.deleteById(userId);
    }

}
