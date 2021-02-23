package com.finartz.tasksapp.repository;

import com.finartz.tasksapp.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findById(Long userId);

    Optional<UserEntity> findByEmail(String email);
}