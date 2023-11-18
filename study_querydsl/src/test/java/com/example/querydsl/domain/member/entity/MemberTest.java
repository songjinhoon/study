package com.example.querydsl.domain.member.entity;

import com.example.querydsl.domain.team.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberTest {

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void testEntity() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        entityManager.persist(teamA);
        entityManager.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);

        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        entityManager.persist(member1);
        entityManager.persist(member2);
        entityManager.persist(member3);
        entityManager.persist(member4);

        // 초기화
        entityManager.flush(); // 쿼리 반영
        entityManager.clear(); // 영속성컨텍스트 캐쉬 지우기

        List<Member> members = entityManager
                .createQuery("select m from Member m", Member.class)
                .getResultList();

        members.forEach(member -> {
            System.out.println("member = " + member);
            System.out.println("-> member.team = " + member.getTeam());
        });
    }
}