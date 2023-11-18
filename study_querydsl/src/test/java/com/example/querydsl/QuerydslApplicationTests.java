package com.example.querydsl;

import com.example.querydsl.domain.dto.MemberSearchCondition;
import com.example.querydsl.domain.dto.MemberTeamDto;
import com.example.querydsl.domain.member.entity.Member;
import com.example.querydsl.domain.member.repository.MemberJpaRepository;
import com.example.querydsl.domain.sample.entity.QSample;
import com.example.querydsl.domain.sample.entity.Sample;
import com.example.querydsl.domain.team.entity.Team;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class QuerydslApplicationTests {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void contextLoads() {
        Sample sample = new Sample();
        entityManager.persist(sample);

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
//        QSample qSample = new QSample("h");
        QSample qSample = QSample.sample;

        Sample result = jpaQueryFactory
                .selectFrom(qSample)
                .fetchOne();

        assertThat(result).isEqualTo(sample);
        assertThat(Objects.requireNonNull(result).getId()).isEqualTo(sample.getId());
    }

    @Test
    public void searchTest() {
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

        MemberSearchCondition memberSearchCondition = new MemberSearchCondition();
        memberSearchCondition.setAgeGoe(35);
        memberSearchCondition.setAgeLoe(40);
        memberSearchCondition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.searchByBuilder(memberSearchCondition);

        for (MemberTeamDto s : result) {
            System.out.println("s = " + s.toString());
        }

        assertThat(result)
                .extracting("username")
                .containsExactly("member4");

    }

    @Test
    public void searchTest2() {
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

        MemberSearchCondition memberSearchCondition = new MemberSearchCondition();
        memberSearchCondition.setAgeGoe(35);
        memberSearchCondition.setAgeLoe(40);
        memberSearchCondition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.search(memberSearchCondition);

        for (MemberTeamDto s : result) {
            System.out.println("s = " + s.toString());
        }

        assertThat(result)
                .extracting("username")
                .containsExactly("member4");

    }

}
