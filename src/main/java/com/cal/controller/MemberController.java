package com.cal.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cal.dto.MemberDto;
import com.cal.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@RestController
@RequestMapping("/member/") // 고정 요청 경로
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") 

public class MemberController { //  클래스 이름 오타도 수정 (MeberController → MemberController)

	private final MemberService service;

	@PutMapping("/register") 
	public ResponseEntity<String> register(@RequestBody MemberDto member) {
		service.register(member);
		return ResponseEntity.ok().body("회원가입 성공");
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody MemberDto m, HttpSession session) {
	    log.info("==== 로그인 API 호출됨 ====");
	    
	    String userId = service.login(m);
	    if (userId == null) {
	        return ResponseEntity.status(401).body(Map.of("message", "로그인 실패"));
	    }
	    
	    //  id로 전체 사용자 DTO 재조회 (role 포함)
	    MemberDto dto = service.findById(userId);
	    if (dto == null) {
	        return ResponseEntity.status(500).body(Map.of("message", "회원 조회 실패"));
	    }

	    // 세션에 DTO 통째로 보관 (키 통일: LOGIN_USER)
	    session.setAttribute("LOGIN_USER", dto);

	    // 프론트가 바로 쓸 수 있게 JSON으로 응답 (id / nickname / role)
	    return ResponseEntity.ok(Map.of(
	        "id", dto.getId(),
	        "nickname", dto.getNickname(),
	        "role", dto.getRole()   // USER | MASTER
	    ));
	}
	
	 @PostMapping("/logout")
	    public ResponseEntity<String> logout(HttpSession session) {
	        session.invalidate();
	        return ResponseEntity.ok("로그아웃 성공");
	}
	
	@GetMapping("/status")
	public ResponseEntity<?> loginStatus(HttpSession session) {
	    MemberDto dto = (MemberDto) session.getAttribute("LOGIN_USER");
	    if (dto == null) {
	        return ResponseEntity.status(401).body(Map.of("message", "NOT_LOGGED_IN"));
	    }
	    return ResponseEntity.ok(Map.of(
	        "id", dto.getId(),
	        "nickname", dto.getNickname(),
	        "role", dto.getRole() // USER | MASTER
	    ));
	}
	@GetMapping("/check-id")
	public ResponseEntity<String> checkId(@RequestParam String id) {
		boolean exists = service.isIdTaken(id);

		if (exists) {
			return ResponseEntity.status(409)
					.header("Content-Type", "text/plain; charset=UTF-8")
					.body("이미 사용 중인 아이디입니다.");
		} else {
			return ResponseEntity
					.ok()
					.header("Content-Type", "text/plain; charset=UTF-8")
					.body("사용 가능한 아이디입니다.");
		}
	}

	@GetMapping("/check-nickname")
	public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
		boolean exists = service.isNicknameTaken(nickname); // isNicknameTaken = is → **"인지?+nickname+Taken 사용중이다

		if (exists) {
			return ResponseEntity.status(409)
					.header("Content-Type", "text/plain; charset=UTF-8")
					.body("이미 사용 중인 닉네임입니다.");
		} else {
			return ResponseEntity
					.ok()
					.header("Content-Type", "text/plain; charset=UTF-8")
					.body("사용 가능한 닉네임입니다.");
		}
	}

	@GetMapping("/check-email")
	public ResponseEntity<String> checkEmail(@RequestParam String email) {
		boolean exists = service.isEmailTaken(email); // MemberService에 구현해야 함

		if (exists) {
			return ResponseEntity.status(409)
					.header("Content-Type", "text/plain; charset=UTF-8")
					.body("이미 사용 중인 이메일입니다.");
		} else {
			return ResponseEntity
					.ok()
					.header("Content-Type", "text/plain; charset=UTF-8")
					.body("사용 가능한 이메일입니다.");
		}
	}
	
          //회원 정보 수정 쪽
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody MemberDto dto, HttpSession session) {
	    MemberDto login = (MemberDto) session.getAttribute("LOGIN_USER");
	    if (login == null) {
	        return ResponseEntity.status(401).body(Map.of("message", "로그인이 필요합니다."));
	    }

	    // 본인만 수정되게 고정
	    String userId = login.getId();
	    dto.setId(userId);

	    // 고정 UPDATE(전컬럼)이라, 빈 값은 기존 값으로 채워서 덮어쓰기 방지
	    MemberDto existing = service.findById(userId);
	    if (existing == null) {
	        return ResponseEntity.status(404).body(Map.of("message", "존재하지 않는 회원입니다."));
	    }

	    // 비번 안 바꾸면 기존 비번 유지
	    if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
	        dto.setPassword(existing.getPassword());
	    }
	    // 닉네임/이메일도 혹시 빈 값이면 기존 유지 (프론트 실수 대비)
	    if (dto.getNickname() == null || dto.getNickname().trim().isEmpty()) {
	        dto.setNickname(existing.getNickname());
	    }
	    if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
	        dto.setEmail(existing.getEmail());
	    }

	    // role은 자기수정에서 변경 불가 → 기존 유지
	    dto.setRole(existing.getRole());

	    try {
	        boolean ok = service.updateMember(dto); // Mapper는 고정 UPDATE
	        if (!ok) {
	            return ResponseEntity.status(500).body(Map.of("message", "회원정보 수정 실패"));
	        }

	        // 세션 최신화 (화면에 바로 새 닉네임 반영되게)
	        MemberDto updated = service.findById(userId);
	        if (updated != null) {
	            session.setAttribute("LOGIN_USER", updated);
	        }

	        // 프론트가 작은 창(알럿) 띄우기 쉽도록 200 OK + 메시지
	        return ResponseEntity.ok(Map.of("message", "회원 정보가 수정되었습니다."));
	    } catch (org.springframework.dao.DataIntegrityViolationException e) {
	        // UNIQUE 충돌(닉네임/이메일 중복 등)
	        return ResponseEntity.status(409).body(Map.of("message", "이미 사용 중인 값이 있습니다."));
	    } catch (Exception e) {
	        return ResponseEntity.status(500).body(Map.of("message", "서버 오류"));
	    }
	}
	
	@GetMapping("/find-by-id")
	public ResponseEntity<MemberDto> findById(@RequestParam String id) {
	    MemberDto member = service.findById(id);

	    if (member != null) {
	        return ResponseEntity.ok(member);
	    } else {
	        return ResponseEntity.status(404).build(); 
	    }
	}
	
	
	
	  @GetMapping("/find-id-by")
	    public ResponseEntity<Map<String, String>> findIdBy(
	            @RequestParam String name,
	            @RequestParam String email) {

	        String id = service.findIdByNameAndEmail(name, email);
	        if (id == null) {
	            return ResponseEntity.status(404)
	                    .body(Map.of("message", "일치하는 정보가 없습니다."));
	        }
	        
	        return ResponseEntity.ok(Map.of(
	                "id", id,              
	                "message", "아이디를 찾았습니다."
	        ));
	    }


	//  비밀번호 찾기 (아이디+이메일 확인 → 임시 비밀번호 발급 & 즉시 응답으로 반환)
	@PostMapping("/reset-password-request")
	public ResponseEntity<Map<String, String>> resetPasswordRequest(@RequestBody Map<String, String> body) {
	    String id = body.get("id");
	    String email = body.get("email");

	    String tempPw = service.issueTempPassword(id, email);
	    if (tempPw == null) {
	        return ResponseEntity.status(404)
	        		.body(Map.of("message", "일치하는 계정이 없습니다."));
	    }
	    // 화면에 보여줄 임시 비밀번호를 그대로 리턴
	    return ResponseEntity.ok(Map.of(
	            "message", "임시 비밀번호가 발급되었습니다.",
	            "tempPassword", tempPw
	    ));
	}
}