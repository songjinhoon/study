package com.study.spring.cloud.userservice.repository;

import com.study.spring.cloud.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
