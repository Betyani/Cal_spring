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
    	// role 누락 대비용임 기본 'USER'
    	if (member.getRole() == null || member
    			.getRole().isBlank()) {member.setRole("USER");
        }
        mapper.insertMember(member);
    }

    @Override
    public String login(MemberDto member) {
        MemberDto dbMember = mapper.findById(member.getId());
        System.out.println("로그인 시도 id: " + member.getId());
        System.out.println("DB에서 찾은 member: " + dbMember);

        if (dbMember != null) {
            System.out.println("DB 패스워드: " + dbMember.getPassword());
            System.out.println("입력한 패스워드: " + member.getPassword());
        }
        if (dbMember != null && dbMember.getPassword().equals(member.getPassword())) {
        	return dbMember.getId();
        }
        return null;
    }
    
    @Override
    public boolean isIdTaken(String id) {
        return mapper.findById(id) != null;
    }
    
    
    @Override
    public boolean isNicknameTaken(String nickname) {
        return mapper.findByNickname(nickname) != null;
    }
    @Override
    public boolean isEmailTaken(String email) {
        return mapper.findByEmail(email) != null;
    }

	
    //로그인   회원 수정 버튼쪽
	public boolean updateMember(MemberDto dto) {
	    // 비밀번호 비워져 있으면 기존 값 유지
	    if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
	        MemberDto existing = mapper.findById(dto.getId());
	    if (dto.getRole() == null || dto.getRole().isBlank()) {
	        dto.setRole(existing.getRole()); // USER | MASTER
	       
	        }
	    }
	    return mapper.updateMember(dto) > 0;
	}

	@Override
	public MemberDto findById(String id) {
	    return mapper.findById(id);
	}
	
	@Override
	public String findIdByNameAndEmail(String name, String email) {
	    return mapper.findIdByNameEmail(name, email);
	}

	
	
	@Override
	public String issueTempPassword(String id, String email) {
	    MemberDto user = mapper.findById(id);
	    if (user == null) return null;
	    if (user.getEmail() == null || !user.getEmail().equalsIgnoreCase(email)) return null;

	    String tempPw = genTempPassword(10); // 임시 비번 길이
	    int updated = mapper.updatePasswordById(id, tempPw); // 현재 프로젝트 평문 저장 정책에 맞춤
	    return updated > 0 ? tempPw : null;
	}

	// 간단 임시 비밀번호 생성(영문+숫자+특수 최소 1개씩)
	private String genTempPassword(int len) {
	    String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	    String nums = "0123456789";
	    String specials = "@$!%*#?&~";
	    String all = letters + nums + specials;

	    StringBuilder sb = new StringBuilder();
	    java.util.Random r = new java.util.Random();           //랜덤 숫자 생성 객체
	    sb.append(letters.charAt(r.nextInt(letters.length())));          
	    sb.append(nums.charAt(r.nextInt(nums.length())));           
	    sb.append(specials.charAt(r.nextInt(specials.length())));   
	    for (int i = 3; i < len; i++) sb.append(all.charAt(r.nextInt(all.length())));
	    return sb.toString();
	}
	
	
}