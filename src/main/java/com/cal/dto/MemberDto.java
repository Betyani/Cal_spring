package com.cal.dto;

import lombok.Data;

@Data
public class MemberDto {
    private String username;  // 사용자 아이디 (로그인 ID)
    private String password;  // 비밀번호
    private String name;      // 사용자 이름
    private String email;     // 이메일
}
