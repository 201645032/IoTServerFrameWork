package kr.inhatc.spring.member.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.member.dto.MFileDto;
import kr.inhatc.spring.member.dto.MemberDto;
public interface MemberService {

	List<MemberDto> memberList();

	void memberInsert(MemberDto member, MultipartHttpServletRequest multipartHttpServletRequest);

	MemberDto memberDetail(String memberIdx);

	void memberUpdate(MemberDto member);

	void memberDelete(String memberIdx);

	MFileDto selectFileInfo(int idx, String memberIdx);

}