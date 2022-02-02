package com.example.querydsl;

import com.example.querydsl.domain.member.entity.Member;
import com.example.querydsl.domain.member.entity.QMember;
import com.example.querydsl.domain.team.entity.Team;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.example.querydsl.domain.member.entity.QMember.member;
import static com.example.querydsl.domain.team.entity.QTeam.*;
import static com.querydsl.jpa.JPAExpressions.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager entityManager;

    JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    public void before() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
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
    }

    @Test
    public void startJPQL() {
        Member member = entityManager.createQuery("select m from Member m where m.username =:username", Member.class)
                .setParameter("username", "member1")
                .getSingleResult();

        assertThat(member.getUsername()).isEqualTo("member1");
    }

    @Test
    public void startQuerydsl() {
        Member findMember = jpaQueryFactory
                .select(member)
                .from(member)
                .where(member.username.eq("member1"))
                .fetchOne();

        assertThat(findMember.getUsername()).isEqualTo("member1");
    }

    @Test
    public void aggregation() {
        List<Tuple> result = jpaQueryFactory
                .select(
                        member.count(),
                        member.age.sum(),
                        member.age.avg(),
                        member.age.max(),
                        member.age.min()
                )
                .from(member)
                .fetch();

        Tuple tuple = result.get(0);
        assertThat(tuple.get(member.count())).isEqualTo(4);
        assertThat(tuple.get(member.age.sum())).isEqualTo(100);
        assertThat(tuple.get(member.age.avg())).isEqualTo(25);
        assertThat(tuple.get(member.age.max())).isEqualTo(40);
        assertThat(tuple.get(member.age.min())).isEqualTo(10);
    }

    @Test
    public void group() throws Exception {
        List<Tuple> result = jpaQueryFactory
                .select(team.name, member.age.avg())
                .from(member)
                .join(member.team, team)
                .groupBy(team.name)
                .fetch();

        Tuple teamA = result.get(0);
        Tuple teamB = result.get(1);

        assertThat(teamA.get(team.name)).isEqualTo("teamA");
        assertThat(teamA.get(member.age.avg())).isEqualTo(15);

        assertThat(teamB.get(team.name)).isEqualTo("teamB");
        assertThat(teamB.get(member.age.avg())).isEqualTo(35);
    }

    @Test
    public void join() {
        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .join(member.team, team)
                .where(team.name.eq("teamA"))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("member1", "member2");
    }

    /*
     * 세타 조인(연관관계가 없는 필드로 조인)
     * 회원의 이름이 팀 이름과 같은 회원 조회
     * 단점: 외부조인이 불가능하다.
     * */
    @Test
    public void theta_join() {
        entityManager.persist(new Member("teamA"));
        entityManager.persist(new Member("teamB"));
        entityManager.persist(new Member("teamC"));

        List<Member> result = jpaQueryFactory
                .select(member)
                .from(member, team)
                .where(member.username.eq(team.name))
                .fetch();

        assertThat(result)
                .extracting("username")
                .containsExactly("teamA", "teamB");
    }

    /*
     * JPQL: select m, t from Member m left join m.team on t.name = 'teamA'
     * */
    @Test
    public void join_on_filtering() {
        List<Tuple> result = jpaQueryFactory
                .select(member, team)
                .from(member)
                .leftJoin(member.team, team).on(team.name.eq("teamA"))
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    /*
     * 나이가 가장 많은 회원 조회
     * */
    @Test
    public void subQuery() {
        QMember memberSub = new QMember("memberSub");

        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.eq(
                        select(memberSub.age.max())
                                .from(memberSub)
                ))
                .fetch();

        assertThat(result)
                .extracting("age")
                .containsExactly(40);
    }

    @Test
    public void subQueryIn() {
        QMember memberSub = new QMember("memberSub");

        List<Member> result = jpaQueryFactory
                .selectFrom(member)
                .where(member.age.in(
                        select(memberSub.age)
                                .from(memberSub)
                                .where(memberSub.age.gt(10))
                ))
                .fetch();

        assertThat(result)
                .extracting("age")
                .containsExactly(20, 30, 40);
    }

    @Test
    public void selectSubQuery() {
        QMember memberSub = new QMember("memberSub");

        List<Tuple> result = jpaQueryFactory
                .select(member.username, select(memberSub.age.avg()).from(memberSub))
                .from(member)
                .fetch();

        for (Tuple tuple : result) {
            System.out.println("tuple = " + tuple);
        }
    }

    @Test
    public void basicCase() {
        List<String> result = jpaQueryFactory
                .select(member.age
                        .when(10).then("열살")
                        .when(20).then("스무살")
                        .otherwise("기타"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void sqlFunction() {
        List<String> result = jpaQueryFactory
                .select(Expressions.stringTemplate(
                        "function('replace', {0}, {1}, {2})",
                        member.username, "member", "M"))
                .from(member)
                .fetch();

        for (String s : result) {
            System.out.println("s = " + s);
        }
    }

    @Test
    public void sqlFunction2() {
        List<String> result = jpaQueryFactory
                .select(member.username)
                .from(member)
                /*.where(member.username.eq(
                        Expressions.stringTemplate("function('lower', {0})", member.username)))*/
                .where(member.username.eq(member.username.lower())) // DB에서 공통적으로 제공하는건 querydsl에서도 지원을해줌
                .fetch();

        for (String s: result) {
            System.out.println("s = " + s);
        }
    }
}

/*
* 1. JPA의 JPQL 혹은 QueryDsl은 from 절 서브쿼리인 인라인 뷰를 지원하지 않는다.
*    - 서브쿼리를 Join으로 변경하거나, 쿼리를 두번 나눠서 실행하거나, native SQL을 사용해서 해결한다.
* 2. 튜플인 repository 계층을 벗어나서 쓰는건 좋지않다. (서비스 컨트롤러단으로 가지않아야함)
* 3. Dto 생성자에 @QueryProjection을 넣으면 compileQuerydsl 필요함
* 4. runtime오류, 컴파일오류
* 5. 동적쿼리 방식
*    - Where절 파라미터 -> 추
*    - BooleanBuilder  -> 비추
* 6. data.sql -> 초기데이터
* 7. 테스트데이터는 InitMember.class 참고
* 8. ctrcl + alt + m // 리팩토링
* */