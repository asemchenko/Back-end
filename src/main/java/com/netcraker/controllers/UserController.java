package com.netcraker.controllers;

import com.netcraker.model.User;
import com.netcraker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class UserController {
    private final UserService userService;

    @GetMapping("profile/{userId}")
    public ResponseEntity<?> getUserProfile(@PathVariable int userId) {
        System.out.println("userId = " + userId);
        final User userFromDb = userService.findByUserId(userId);
        if (userFromDb == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with such id doesn't exist");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userFromDb);
    }
}