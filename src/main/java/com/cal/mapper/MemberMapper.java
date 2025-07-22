package com.cal.mapper;

import com.cal.dto.MemberDto;

public interface MemberMapper {
    void insertMember(MemberDto member);
    MemberDto findByUsername(String username);
}
