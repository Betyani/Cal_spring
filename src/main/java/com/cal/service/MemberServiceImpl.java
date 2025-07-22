package com.cal.service;

import org.springframework.stereotype.Service;
import com.cal.dto.MemberDto;
import com.cal.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper mapper;

    @Override
    public void register(MemberDto member) {
        mapper.insertMember(member);
    }

    @Override
    public String login(MemberDto member) {
        MemberDto dbMember = mapper.findByUsername(member.getUsername());
        System.out.println("로그인 시도 username: " + member.getUsername());
        System.out.println("DB에서 찾은 member: " + dbMember);

        if (dbMember != null) {
            System.out.println("DB 패스워드: " + dbMember.getPassword());
            System.out.println("입력한 패스워드: " + member.getPassword());
        }
        if (dbMember != null && dbMember.getPassword().equals(member.getPassword())) {
            return dbMember.getUsername();
        }
        return null;
    }
}
