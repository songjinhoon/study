package com.study.spring.cloud.userservice.domain.repository;

import com.study.spring.cloud.userservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByAccount(String account);

}
