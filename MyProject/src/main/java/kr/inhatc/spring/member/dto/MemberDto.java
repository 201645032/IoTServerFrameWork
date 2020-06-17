package kr.inhatc.spring.member.dto;

import java.util.List;

import kr.inhatc.spring.member.dto.MFileDto;
import lombok.Data;

@Data
public class MemberDto {
	// 멤버 정보 관련
	private String memberId;
	private String email;
	private String enabled;
	private String joinDate;
	private String name;
	private String pw;
	private String role;
	
	// 멤버 사진 관련
	private List<MFileDto> fileList;
	
}