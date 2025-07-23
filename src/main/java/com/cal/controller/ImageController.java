package com.cal.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/image")
@RestController
public class ImageController {
	
	private final String uploadDir = "C:/projectImage/";

    @RequestMapping("/upload")
    public Map<String, String> upload(@RequestParam("file") MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File saveFile = new File(uploadDir + fileName);
        try {
        file.transferTo(saveFile); // 파일 저장
        
        
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        Map<String, String> result = new HashMap<>();
        result.put("fileName", fileName);
        
        return result;
    }
    
 // HTTP 요청 중 /image/파일명 으로 들어온 요청을 처리하는 메서드
    @RequestMapping("/load/{imageUrl:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageUrl) {
        try {
            // 자바의 NIO를 사용하여 실제 이미지 파일 경로를 생성
            // 예: "C:/projectImage/abc.jpg"
            Path path = Paths.get(uploadDir + imageUrl);
            
            log.info("요청 파일명: " + imageUrl);
            log.info("조립된 경로: " + path.toString());
            System.out.println("절대경로: " + path.toAbsolutePath());
            log.info("파일 존재 여부: " + Files.exists(path));


            // 해당 경로를 URL 형식으로 변환하여 Resource로 감쌈
            // Resource는 스프링에서 파일, 클래스패스, URL 등을 다룰 수 있는 추상 객체
            Resource resource = new UrlResource(path.toUri());

            // 파일이 존재하지 않으면 404 응답 반환
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // 파일의 MIME 타입을 자동으로 감지 (예: image/jpeg, image/png 등)
            String contentType = Files.probeContentType(path);

            // 정상적으로 존재하는 파일일 경우 200 OK + Content-Type 헤더 + 파일 본문을 응답
            return ResponseEntity.ok()
					.contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (IOException e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        
        
    }

    
	
}
