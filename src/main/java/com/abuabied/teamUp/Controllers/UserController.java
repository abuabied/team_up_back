package com.abuabied.teamUp.Controllers;

import com.abuabied.teamUp.Entities.User;
import com.abuabied.teamUp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = "*", allowCredentials = "true", maxAge = 3600)
//@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "Requestor-Type", exposedHeaders = "*")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<String> test() throws Exception {
        return new ResponseEntity<>("hi", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> loginUser(@RequestBody User user) throws Exception {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return userService.authUser(user);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerUser(@RequestBody User user) throws Exception {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return userService.registerUser(user);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) throws Exception {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return userService.updateUser(user);
    }

    @PostMapping("/get-user")
    public ResponseEntity<Optional<User>> getUser(@RequestBody User user) throws Exception {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return userService.getUser(user);
    }
}
