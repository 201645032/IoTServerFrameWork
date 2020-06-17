package kr.inhatc.spring.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.dto.FileDto;
import kr.inhatc.spring.member.dto.MFileDto;
import kr.inhatc.spring.member.dto.MemberDto;
import kr.inhatc.spring.member.mapper.MemberMapper;
import kr.inhatc.spring.utils.FileUtils;
import kr.inhatc.spring.utils.MFileUtils;

@Service
public class MemberServiceImpl implements MemberService {

	//멤버 Mapper 가져오기
	//메모리 자동 등록
	@Autowired
	private MemberMapper memberMapper;
	
	//파일 관리를 위한 기능 가져오기
	private MFileUtils fileUtils = new MFileUtils();
	
	//멤버 리스트 불러오기 서비스
	@Override
	public List<MemberDto> memberList() {
		// TODO Auto-generated method stub
		return memberMapper.memberList();
	}
	
	//멤버 추가 서비스
	@Override
	public void memberInsert(MemberDto member, MultipartHttpServletRequest multipartHttpServletRequest) {
		// TODO Auto-generated method stub
		memberMapper.memberInsert(member);
		
		// 파일 저장
		List<MFileDto> list = fileUtils.parseFileInfo(member.getMemberId(), multipartHttpServletRequest);

		// DB에 파일 저장
		if(!CollectionUtils.isEmpty(list)) {
			memberMapper.memberFileInsert(list);
		}
		
	}
	
	//멤버 조회 서비스
	@Override
	public MemberDto memberDetail(String memberId) {
		// TODO Auto-generated method stub
		MemberDto member = memberMapper.memberDetail(memberId);
		
		//파일 정보를 불러오고 보드에 설정 후 반환
		List<MFileDto> fileList = memberMapper.selectMemberFileList(memberId);
		member.setFileList(fileList);
		
		return member;
	}

	//멤버 수정 서비스
	@Override
	public void memberUpdate(MemberDto member) {
		// TODO Auto-generated method stub
		memberMapper.memberUpdate(member);
	}
	
	//멤버 등록해제 서비스
	@Override
	public void memberDelete(String memberId) {
		// TODO Auto-generated method stub
		memberMapper.memberDelete(memberId);
	}

	//멤버 프로필사진 다운로드 서비스
	@Override
	public MFileDto selectFileInfo(int idx, String memberId) {
		// TODO Auto-generated method stub
		MFileDto memberFile = memberMapper.selectFileInfo(idx, memberId);
		return memberFile;
	}

}
