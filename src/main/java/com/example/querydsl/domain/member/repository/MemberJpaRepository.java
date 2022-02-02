package com.example.querydsl.domain.member.repository;

import com.example.querydsl.domain.dto.MemberSearchCondition;
import com.example.querydsl.domain.dto.MemberTeamDto;
import com.example.querydsl.domain.dto.QMemberTeamDto;
import com.example.querydsl.domain.member.entity.Member;
import com.example.querydsl.domain.member.entity.QMember;
import com.example.querydsl.domain.team.entity.QTeam;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.example.querydsl.domain.member.entity.QMember.*;
import static com.example.querydsl.domain.team.entity.QTeam.*;
import static org.springframework.util.StringUtils.*;

@Repository
public class MemberJpaRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    public MemberJpaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    /*public MemberJpaRepository(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        this.entityManager = entityManager;
        this.jpaQueryFactory = jpaQueryFactory;
    }*/

    public void save(Member member) {
        entityManager.persist(member);
    }

    public Optional<Member> findById(Long id) {
        Member findMember = entityManager.find(Member.class, id);
        return Optional.ofNullable(findMember);
    }

    public List<Member> findAll() {
        return entityManager.createQuery("select m from Member m", Member.class).getResultList();
    }

    public List<Member> findAll_querydsl() {
        return jpaQueryFactory
                .selectFrom(member)
                .fetch();
    }

    public List<Member> findByUsername(String username) {
        return entityManager.createQuery("select m from Member m where m.username = :username", Member.class)
                .setParameter("username", username)
                .getResultList();
    }

    public List<Member> findByUsername_querydsl(String username) {
        return jpaQueryFactory
                .selectFrom(member)
                .where(member.username.eq(username))
                .fetch();
    }

    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition memberSearchCondition) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (hasText(memberSearchCondition.getUsername())) {
            booleanBuilder.and(member.username.eq(memberSearchCondition.getUsername()));
        }
        if (hasText(memberSearchCondition.getTeamName())) {
            booleanBuilder.and(team.name.eq(memberSearchCondition.getTeamName()));
        }
        if (memberSearchCondition.getAgeGoe() != null) {
            booleanBuilder.and(member.age.goe(memberSearchCondition.getAgeGoe()));
        }
        if (memberSearchCondition.getAgeLoe() != null) {
            booleanBuilder.and(member.age.loe(memberSearchCondition.getAgeLoe()));
        }

        return jpaQueryFactory
                .select(new QMemberTeamDto(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name
                ))
                .from(member)
                .leftJoin(member.team, team)
                .where(booleanBuilder)
                .fetch();
    }

    public List<MemberTeamDto> search(MemberSearchCondition condition) {
        return jpaQueryFactory
                .select(new QMemberTeamDto(
                        member.id,
                        member.username,
                        member.age,
                        team.id,
                        team.name))
                .from(member)
                .leftJoin(member.team, team)
                .where(
                        usernameEq(condition.getUsername()),
                        teamNameEq(condition.getTeamName()),
                        ageGoe(condition.getAgeGoe()),
                        ageLoe(condition.getAgeLoe()))
                .fetch();
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe == null ? null : member.age.goe(ageGoe);
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe == null ? null : member.age.loe(ageLoe);
    }

    /*private BooleanExpression usernameEq(String username) {
        return isEmpty(username) ? null : member.username.eq(username);
    }

    private BooleanExpression teamNameEq(String teamName) {
        return isEmpty(teamName) ? null : team.name.eq(teamName);
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return ageGoe == null ? null : member.age.goe(ageGoe);
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return ageLoe == null ? null : member.age.loe(ageLoe);
    }*/
}
