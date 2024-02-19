package com.system.bike_rental_system.repo;

import com.system.bike_rental_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    @Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE status = ?1", nativeQuery = true)
    List<User> allUsers(String status);

    @Query(value = "UPDATE users set password = ?1 where email = ?2", nativeQuery = true)
    void updatePassword(String updatedPassword, String email);
}
