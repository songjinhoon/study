package com.study.jwttutorial.domain.usr.repository;

import com.study.jwttutorial.domain.usr.entity.Usr;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsrRepository extends JpaRepository<Usr, Long> {

    @EntityGraph(attributePaths = "auths") // FetchType = EAGER
    Optional<Usr> findOneWithAuthsByNm(String nm);

}
