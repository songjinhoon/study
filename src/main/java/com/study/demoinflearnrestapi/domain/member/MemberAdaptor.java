package com.study.demoinflearnrestapi.domain.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

public class MemberAdaptor extends User {

    private Member member;

    public MemberAdaptor(Member member) {
        super(member.getAccount(), member.getPassword(), member.getAuthorities());
        this.member = member;
    }

    public Member getMember() {
        return member;
    }

}
