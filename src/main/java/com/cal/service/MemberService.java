package com.cal.service;

import com.cal.dto.MemberDto;

public interface MemberService {
    void register(MemberDto member);
    String login(MemberDto member);
    boolean isNicknameTaken(String nickname);
	boolean isIdTaken(String id);
	boolean isEmailTaken(String email);
	boolean updateMember(MemberDto dto);
	MemberDto findById(String id);
	String findIdByNameAndEmail(String name, String email);
	String issueTempPassword(String id, String email);
}
