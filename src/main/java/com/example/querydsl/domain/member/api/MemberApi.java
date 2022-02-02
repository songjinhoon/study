package com.example.querydsl.domain.member.api;

import com.example.querydsl.domain.dto.MemberSearchCondition;
import com.example.querydsl.domain.dto.MemberTeamDto;
import com.example.querydsl.domain.member.repository.MemberJpaRepository;
import com.example.querydsl.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberApi {

    private final MemberRepository memberRepository;

    private final MemberJpaRepository memberJpaRepository;

    @GetMapping(value = "/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.search(condition);
    }

    @GetMapping(value = "/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCondition condition, Pageable pageable) {
        return memberRepository.searchPageSimple(condition, pageable);
    }

    @GetMapping(value = "/v3/members")
    public Page<MemberTeamDto> searchMemberV3(MemberSearchCondition condition, Pageable pageable) {
        return memberRepository.searchPageComplex(condition, pageable);
    }
}
