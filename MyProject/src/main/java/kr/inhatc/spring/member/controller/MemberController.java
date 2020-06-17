package kr.inhatc.spring.member.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.member.dto.MemberDto;
import kr.inhatc.spring.member.dto.MFileDto;
import kr.inhatc.spring.member.dto.MemberDto;
import kr.inhatc.spring.member.service.MemberService;

@Controller
public class MemberController {
	
	//로그 출력 설정
	//org.slf4j 패키지에서 가져오기
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 멤버 서비스 가져오기
	// 메모리에 자동으로 등록
	@Autowired
	private MemberService memberService;
	
	// 멤버 리스트 불러오기
	@RequestMapping("/member/memberList")
	public String memberList(Model model)
	{
		//DB테이블의 리스트를 불러온뒤 뷰에 적용한다.
		List<MemberDto> list = memberService.memberList();
		model.addAttribute("list", list);
		return "member/memberList";
	}
	
	@RequestMapping("/member/memberWrite")
	public String memberWrite()
	{
		return "member/memberWrite";
	}
	
	// redirect를 이용해 웹피이지로 단순 이동이 아니라 설정한 서비스를 다시 시작하는것이 가능하다.
	// 데이터를 MemberDto 형식으로 받아오고 전달한다.
	// 파일도 함께 받아오면 처리하도록 설정
	@RequestMapping("/member/memberInsert")
	public String memberInsert(MemberDto member, MultipartHttpServletRequest multipartHttpServletRequest)
	{
		memberService.memberInsert(member, multipartHttpServletRequest);
		return "redirect:/member/memberList";
	}
	
	//멤버의 상세내용 보기
	@RequestMapping("/member/memberDetail")
	public String memberDetail(@RequestParam String memberId, Model model)
	{
		//멤버 번호를 매개변수로, 서비스 호출 후 결과 저장
		MemberDto member = memberService.memberDetail(memberId);
		//모델을 통해 데이터 전달
		model.addAttribute("member", member);
		return "member/memberDetail";
	}
	
	//멤버 정보 수정
	@RequestMapping("/member/memberUpdate")
	public String memberUpdate(MemberDto member)
	{
		memberService.memberUpdate(member);
		return "redirect:/member/memberList";
	}
	
	//멤버 정보 삭제
	@RequestMapping("/member/memberDelete")
	public String memberDelete(@RequestParam("memberId") String memberId)
	{
		memberService.memberDelete(memberId);
		return "redirect:/member/memberList";
	}
	
	//파일 다운로드
	//파일번호와 대상 멤버의 인식번호를 가져온다.
	@RequestMapping("/member/downloadMemberFile")
	public void downloadmemberFile(
			@RequestParam("idx") int idx,
			@RequestParam("memberIdx") String memberId,
			HttpServletResponse response) throws Exception
	{
		//파일에 관한 정보를 저장한다.
		MFileDto memberFile = memberService.selectFileInfo(idx, memberId);
		
		//파일 정보가 빈정보가 아닐경우 실행
		if(ObjectUtils.isEmpty(memberFile) == false)
		{
			String fileName = memberFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(memberFile.getStoredFilePath()));
			
			// response 헤더에 설정
			response.setContentType("application/octet-stream");
			response.setContentLength(files.length);
			response.setHeader("Content-Disposition", "attachment; filename=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binart");
			
			//파일 작성
			response.getOutputStream().write(files);
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}
	}

}
