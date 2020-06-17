package kr.inhatc.spring.utils;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.inhatc.spring.member.dto.MFileDto;

@Component
public class MFileUtils {
	
	public List<MFileDto> parseFileInfo(String memberId, MultipartHttpServletRequest multipartHttpServletRequest){
		if(ObjectUtils.isEmpty(multipartHttpServletRequest)) {

			return null;
		}
		List<MFileDto> fileList = new ArrayList<MFileDto>();
		
		//파일이 업로드될 폴더 생성
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/" + current.format(format);
		
		File file = new File(path);
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iter = multipartHttpServletRequest.getFileNames();
		
		String originalFileExtension = null;
		while(iter.hasNext()) {
			List<MultipartFile> list = multipartHttpServletRequest.getFiles(iter.next());
			
			for (MultipartFile multipartFile : list) {

				if(multipartFile.isEmpty() == false) {
					
					String contentType = multipartFile.getContentType();
					if(ObjectUtils.isEmpty(contentType)) {
						break;
					}else {
						if(contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if(contentType.contains("image/jpg")) {
							originalFileExtension = ".png";
						} else if(contentType.contains("image/jpg")) {
							originalFileExtension = ".gif";
						} else {
							break;
						}
					}
					
					String newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					MFileDto memberFile = new MFileDto();
					memberFile.setMemberId(memberId);
					memberFile.setFileSize(multipartFile.getSize());
					memberFile.setOriginalFileName(multipartFile.getOriginalFilename());
					memberFile.setStoredFilePath(path + "/" + newFileName);
					fileList.add(memberFile);
					
					file = new File(path + "/" + newFileName);
					try {
						multipartFile.transferTo(file);
					} catch (IllegalStateException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		return fileList;
	}
}