package kr.inhatc.spring.board.service;

import java.util.List;

import org.apache.ibatis.javassist.bytecode.Descriptor.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.board.dto.BoardDto;
import kr.inhatc.spring.board.dto.FileDto;
import kr.inhatc.spring.board.mapper.BoardMapper;
import kr.inhatc.spring.utils.FileUtils;

@Service
public class BoardServiceimpl implements BoardService {
	@Autowired
	private  BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDto> boardList() {
		// TODO Auto-generated method stub
		return boardMapper.boardList(); 
	}

	@Override
	public void boardInsert(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) {
		// TODO Auto-generated method stub
		boardMapper.boardInsert(board);
		
		//1. 파일 저장 2. 디비저장
		List<FileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.boardFileInsert(list);
		}
		
	}

	@Override
	public BoardDto boardDetail(int boardIdx) {
		// TODO Auto-generated method stub
		BoardDto board = boardMapper.boardDetail(boardIdx);
		// 파일 정보를 넣을 겁니다.
		List<FileDto> filstList = boardMapper.selectBoardFileList(boardIdx);
		board.setFileList(filstList);
		
		boardMapper.updateHit(boardIdx);
		return board;
	}

	@Override
	public void boardUpdate(BoardDto board) {
		boardMapper.boardUpdate(board);
	}

	@Override
	public void boardDelete(int boardIdx) {
		// TODO Auto-generated method stub
		boardMapper.boardDelete(boardIdx);
		
	}

	@Override
	public FileDto selectFileInfo(int idx, int boardIdx) {
		FileDto boardFile = boardMapper.selectFileInfo(idx,boardIdx);
		return boardFile;
	}


	@Override
	public List<BoardDto> boardSerach(String title) {
		// TODO Auto-generated method stub
		return boardMapper.boardSerach(title);
	}
	

}
