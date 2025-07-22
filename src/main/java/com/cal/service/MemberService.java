package com.cal.service;

import com.cal.dto.MemberDto;

public interface MemberService {
    void register(MemberDto member);
    String login(MemberDto member);
}
