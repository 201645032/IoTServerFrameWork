package kr.inhatc.spring.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.dto.BoardDto;

public interface BoardService {

	java.util.List<kr.inhatc.spring.board.dto.BoardDto> boardList();

	void boardInsert(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest);

	BoardDto boardDetail(int boardIdx);

	void boardUpdate(BoardDto board);

	void boardDelete(int boardIdx);

	kr.inhatc.spring.board.dto.FileDto selectFileInfo(int idx, int boardIdx);

	List<BoardDto> boardSerach(String title);


	

}
	