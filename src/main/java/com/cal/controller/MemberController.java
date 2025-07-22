package com.cal.controller;

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

public class MemberController { // 🔔 클래스 이름 오타도 수정 (MeberController → MemberController)

	private final MemberService service;

	@PutMapping("/register") 
	public ResponseEntity<String> register(@RequestBody MemberDto member) {
		service.register(member);
		return ResponseEntity.ok().body("회원가입 성공");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody MemberDto m, HttpSession session,
			@RequestParam(value = "saveId", required = false) String saveId, HttpServletResponse response) {
		log.info("==== 로그인 저장 체크: " + saveId);
		log.info("==== 로그인 API 호출됨 ====");
	    log.info("Request username: " + m.getUsername());
	    log.info("Request password: " + m.getPassword());

	  

		String username = service.login(m);
		if (username != null) {
			session.setAttribute("loggedInUser", username);
			if ("on".equals(saveId)) {
				Cookie c = new Cookie("cookieSavedId", m.getUsername()); // 여기 로그인 쿠키 
				c.setPath("/");
				c.setHttpOnly(true);
		        c.setMaxAge(60 * 60 * 24 * 30);
				response.addCookie(c);
			}
			return ResponseEntity.ok("로그인 성공");
		} else {
			return ResponseEntity.status(401).body("로그인 실패");
		}
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(HttpSession session, HttpServletResponse response) {
		session.invalidate();
		 // 🔹 cookieSavedId 쿠키 삭제
	    Cookie deleteCookie = new Cookie("cookieSavedId", null);
	    deleteCookie.setPath("/");       // 반드시 동일한 path
	    deleteCookie.setMaxAge(0);       // 0 → 즉시 삭제
	    response.addCookie(deleteCookie);

		return ResponseEntity.ok("로그아웃 성공");
	}
	
	@GetMapping("/status")
	public ResponseEntity<String> loginStatus(HttpSession session) {
	    String loggedInUser = (String) session.getAttribute("loggedInUser");
	    if (loggedInUser != null) {
	        return ResponseEntity.ok("현재 로그인한 사용자: " + loggedInUser);
	    } else {
	        return ResponseEntity.status(401).body("로그인하지 않음");
	    }
	}
	
	
}
	
	

