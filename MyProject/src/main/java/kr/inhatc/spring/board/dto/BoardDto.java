package kr.inhatc.spring.board.dto;
// DTO : Data Transfer Object 

import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
public class BoardDto {
	
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private String createdDatetime;
	// 파일 관리를 위한 리스트 추가
	private List<FileDto> fileList;
	
}
