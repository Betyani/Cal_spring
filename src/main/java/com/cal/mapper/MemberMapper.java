package com.cal.mapper;

import org.apache.ibatis.annotations.Param;

import com.cal.dto.MemberDto;

public interface MemberMapper {
    void insertMember(MemberDto member);
    MemberDto findById(String id);
    MemberDto findByNickname(String nickname);
    MemberDto findByEmail(String email);
	int updateMember(MemberDto dto);
	String findIdByNameEmail(@Param("name") String name,
                             @Param("email") String email);

    int updatePasswordById(@Param("id") String id,
                           @Param("password") String password);
}
