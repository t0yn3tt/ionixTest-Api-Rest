package com.edgardo.ionixtest.repositories;

import com.edgardo.ionixtest.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public Boolean existsByEmail(String email);
}
