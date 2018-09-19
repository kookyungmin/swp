package com.gguri.swp.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	//파일을 저장하는 경로
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
		
	@RequestMapping(value = "/uploadForm", method = RequestMethod.GET)
	public void uploadFormGET() throws Exception{
		logger.info("uploadForm get.....");
	}
	@RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
	public void uploadFormpost(MultipartFile file, Model model,
							   @RequestParam String type) throws Exception{
		logger.info("uploadForm post.....originalName={}, size={}, contentType={}",
				file.getOriginalFilename(),
				file.getSize(),
				file.getContentType());
		
		String savedFileName = uploadFile(file);
		model.addAttribute("savedFileName", savedFileName);
		model.addAttribute("type", type);
	}
	
	//원본 파일의 이름과 파일 데이터를 byte[]로 변환한 정보를 파라미터로 처리해서 실제로 업로딩
	private String uploadFile(MultipartFile file) throws IOException{
		//파일이름을 중복되지 않는 값으로 변경
		String filename = UUID.randomUUID().toString() 
				+ "_" 
				+ file.getOriginalFilename();
		//파일 저장되는 경로에 파일 생성 후
		File target = new File(uploadPath, filename);
		//바이트 파일 복사
		FileCopyUtils.copy(file.getBytes(), target);
		return filename;
	}
	
	
}
