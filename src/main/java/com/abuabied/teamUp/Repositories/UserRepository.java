package com.abuabied.teamUp.Repositories;

import com.abuabied.teamUp.Entities.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
