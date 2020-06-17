package kr.inhatc.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import ch.qos.logback.classic.Logger;
import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.dto.FileDto;
import kr.inhatc.spring.board.service.BoardService;

@Controller
public class BoardController {
	
	private org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BoardService boardService;
	
	
//	@RequestMapping("/")
//	public String hello() {
//		return "index";
//	}
	
	@RequestMapping("/board/boardList.do")
	public String boardList(Model model) {
		List<BoardDto> list = boardService.boardList();
		log.debug("=======>" + list.size());
		model.addAttribute("list", list);
		return "board/boardList";
	}
	
	@RequestMapping("/board/boardWrite")
	public String boardWrite(Model model) {
		return "board/boardWrite";
	}
	@RequestMapping("/board/boardInsert")
	public String boardInsert(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) {
		boardService.boardInsert(board, multipartHttpServletRequest);
		return "redirect:/board/boardList.do";
	}
	@RequestMapping("/board/boardDetail")
	public String boardDetail(@RequestParam int boardIdx, Model model) {
		BoardDto board = boardService.boardDetail(boardIdx);
		model.addAttribute("board", board);
		return "board/boardDetail";
	}
	
	
	@RequestMapping("/board/boardUpdate")
	public String boardUpdate(BoardDto board) {
		boardService.boardUpdate(board);
		return "redirect:/board/boardList.do";
	}
	
	@RequestMapping("/board/boardDelete")
	public String boardDelete(@RequestParam("boardIdx") int boardIdx) {
		boardService.boardDelete(boardIdx);
		return "redirect:/board/boardList.do";
	}
	
	@RequestMapping("/board/downloadBoardFile")
	public void downloadBoardFile(
			@RequestParam("idx") int idx
			,@RequestParam("boardIdx") int boardIdx,
			HttpServletResponse response) throws Exception {
		FileDto boardFile = boardService.selectFileInfo(idx,boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false)
		{
			String fileName = boardFile.getOriginalFileName();
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
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
	
	@RequestMapping("/board/boardSerach")
	public String boardSerach(@RequestParam String title, Model model) {
		List<BoardDto> list = boardService.boardSerach(title);
		model.addAttribute("list", list);
		return "board/boardSerach";
	}
}
