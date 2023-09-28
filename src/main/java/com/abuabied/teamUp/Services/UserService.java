package com.abuabied.teamUp.Services;

import com.abuabied.teamUp.Entities.User;
import com.abuabied.teamUp.Helpers.HelperFunctions;
import com.abuabied.teamUp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("user")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class UserService {
    // let framework know to initialize the object for us
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<HttpStatus> authUser(User user) {
        Optional<User> checkUser = checkIfUserExists(user);
        if (!checkUser.isEmpty()) {
            try {
                if (HelperFunctions.comparePasswords(user.getPassword(), checkUser.get().getPassword()) == 0) {
                    return createGoodResponse(user.getUsername(), HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            } catch (Exception err) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<HttpStatus> registerUser(User user) {
        if (checkIfUserExists(user).isEmpty()) {
            String hashedPassword = HelperFunctions.hashPassword(user.getPassword());
            user.setPassword(hashedPassword);
            User newUser = userRepository.save(user);
            if (newUser != null) {
                return createGoodResponse(user.getUsername(), HttpStatus.CREATED);
            }
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    public ResponseEntity<HttpStatus> updateUser(User user) {
        try {
            if (userRepository.save(user) != null) {
                return createGoodResponse(user.getUsername(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Optional<User>> getUser(User user) {
        try {
            Optional<User> checkUser = userRepository.findByUsername(user.getUsername());
            if (!checkUser.isEmpty()) {
                checkUser.get().setPassword("");
                return new ResponseEntity<>(checkUser, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> test() {
        try {
            ResponseCookie cookie = HelperFunctions.createTestCookie();
            ResponseEntity response = ResponseEntity
                    .status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .build();
            return response;
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private Optional<User> checkIfUserExists(User user) {
        Optional<User> checkUser = Optional.empty();
        try {
            if (!user.getUsername().toString().isEmpty()) {
                checkUser = userRepository.findByUsername(user.getUsername());
            } else if (!user.getEmail().toString().isEmpty()) {
                checkUser = userRepository.findByEmail(user.getEmail());
            }
        } catch (Exception err) {
            return Optional.empty();
        }
        return checkUser;
    }

    private ResponseEntity<HttpStatus> createGoodResponse(String username, HttpStatus status) {
        ResponseCookie cookie = HelperFunctions.createCookieForUser(username);
        ResponseEntity response = ResponseEntity
                .status(status)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .build();
        return response;
    }
}
