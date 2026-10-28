package com.example.spring_course.repository;

import com.example.spring_course.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query ("SELECT u FROM UserEntity u WHERE u.firstName = :firstName")
    List<UserEntity> searchByName(@Param("firstName") String firstName);

    @Query (value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    List<UserEntity> searchByEmail(@Param("email") String email);
}
