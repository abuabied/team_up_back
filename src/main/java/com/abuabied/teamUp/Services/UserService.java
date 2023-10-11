package com.abuabied.teamUp.Services;

import com.abuabied.teamUp.Entities.Game;
import com.abuabied.teamUp.Entities.User;
import com.abuabied.teamUp.Helpers.HelperFunctions;
import com.abuabied.teamUp.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@Service("user")
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class UserService {
    // let framework know to initialize the object for us
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<HttpStatus> authUser(User user) {
        try {
            Optional<User> checkUser = checkIfUserExists(user);
            if (!checkUser.isEmpty()) {
                if (HelperFunctions.comparePasswords(user.getPassword(), checkUser.get().getPassword()) == 0) {
                    String gameIDsString = HelperFunctions
                            .getGameCollectionItemsAsIDString(checkUser.get().getGameCollection());
                    return createGoodResponse(gameIDsString, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<HttpStatus> registerUser(User user) {
        try {
            if (checkIfUserExists(user).isEmpty()) {
                String hashedPassword = HelperFunctions.hashPassword(user.getPassword());
                user.setPassword(hashedPassword);
                user.setGameCollection(new HashMap<>());
                User newUser = userRepository.save(user);
                if (newUser != null) {
                    return createGoodResponse(user.getUsername(), HttpStatus.CREATED);
                }
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<HttpStatus> updateUser(User user) {
        try {
            if (userRepository.save(user) != null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<User> getUser(User user) {
        try {
            Optional<User> checkUser = userRepository.findByUsername(user.getUsername());
            if (!checkUser.isEmpty()) {
                checkUser.get().setPassword("");
                return new ResponseEntity<>(checkUser.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> addGameToCollection(User user, Game game) {
        try {
            Optional<User> userToUpdate = userRepository.findByUsername(user.getUsername());
            if (!userToUpdate.isEmpty()) {
                if (userToUpdate.get().getGameCollection() == null) {
                    userToUpdate.get().setGameCollection(new HashMap<>());
                }
                if (!userToUpdate.get().getGameCollection().containsKey(game.getGameID())) {
                    userToUpdate.get().getGameCollection().put(game.getGameID(), game);
                }
                if (userRepository.save(userToUpdate.get()) != null) {
                    String gameIDsString = HelperFunctions
                            .getGameCollectionItemsAsIDString(userToUpdate.get().getGameCollection());
                    return new ResponseEntity<>(gameIDsString, HttpStatus.OK);
                }
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<String> removeGameFromCollection(User user, Game game) {
        try {
            Optional<User> userToUpdate = userRepository.findByUsername(user.getUsername());
            if (!userToUpdate.isEmpty()) {
                if (userToUpdate.get().getGameCollection() != null) {
                    userToUpdate.get().getGameCollection().remove(game.getGameID());
                    if (userRepository.save(userToUpdate.get()) != null) {
                        String gameIDsString = HelperFunctions
                                .getGameCollectionItemsAsIDString(userToUpdate.get().getGameCollection());
                        return new ResponseEntity<>(gameIDsString, HttpStatus.OK);
                    } else {
                        return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

        public ResponseEntity<Collection<Game>> getGamesCollection(User user) {
        try {
            Optional<User> checkUser = userRepository.findByUsername(user.getUsername());
            if (!checkUser.isEmpty()) {
                checkUser.get().setPassword("");
                return new ResponseEntity<>(checkUser.get().getGameCollection().values(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
        // ResponseCookie cookie = HelperFunctions.createCookieForUser(username);
        ResponseEntity<HttpStatus> response = new ResponseEntity<>(status);
        return response;
    }
}
