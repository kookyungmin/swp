package com.gguri.swp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gguri.swp.service.BoardService;
import com.gguri.swp.util.FileUtils;

@Controller
public class UploadController {
	
	@Inject 
	private BoardService service;
	
	//파일을 저장하는 경로
	@Resource(name = "uploadPath")
	private String uploadPath;
	
	@Resource(name = "uploadDirectPath")
	private String uploadDirectPath;
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
		
	@RequestMapping(value = "/uploadAjax", method = RequestMethod.POST)
	public ResponseEntity<String[] > uploadFormAjax(MultipartFile[] files, Integer bno,
											        boolean isDirect) throws Exception{
		int len = files == null ? 0 : files.length;
		logger.info("uploadForm Ajax.....files.length={} isDirect={}", len, isDirect);
		
		try {
			String[] uploadedFiles = new String[len];
			for(int i = 0; i < len; i++) {
				String updir = isDirect ? uploadDirectPath : uploadPath;
				uploadedFiles[i] = FileUtils.uploadFile(files[i], updir);
			}
			if(bno != null) {
				service.appendAttach(uploadedFiles, bno);
			}
			return new ResponseEntity<>(uploadedFiles, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(new String[] { e.getMessage() }, HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/displayFile")
	public ResponseEntity<byte[]> displayFile(String fileName, boolean isDirect) throws Exception{
		
		logger.info("download Ajax.....fileName={}, isDirect={}", fileName, isDirect);
		String updir = isDirect ? uploadDirectPath : uploadPath;
		
		//경로에 있는 파일을 읽어옴
		try(InputStream in = new FileInputStream(updir + fileName)) {
			String formatName = FileUtils.getFileExtension(fileName);
			MediaType mType = FileUtils.getMediaType(formatName);
			HttpHeaders headers = new HttpHeaders();
			if (mType != null) { //이미지인 경우
				headers.setContentType(mType);
			} else {
				fileName = fileName.substring(fileName.indexOf("_") + 1);
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				//UTF-8로 쪼갠 다음 IOS-8859-1로 바꾸어줌(파일 내려갈 때 안 깨짐)
				String dsp = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
				headers.add("Content-Disposition", 
						"attachment; filename=\"" + dsp + "\"");
			}
			return new ResponseEntity<>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		}catch(Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteFile", method= RequestMethod.DELETE)
	public ResponseEntity<String> deleteFile(String fileName, Integer bno) throws Exception{
		
		logger.info("download Ajax.....deleteFile={} bno={}", fileName, bno);
		
		try {
			if(bno != null) {
				service.deleteAttach(fileName);
			}
			
			boolean isImage = FileUtils.getMediaType(FileUtils.getFileExtension(fileName)) != null;
			if (isImage) {
				File thumb = new File(uploadPath + fileName);
				thumb.delete();
				
				// /2018/09/27/s_abc.jpg -> /2018/09/27/abc.jpg
				int lastSlash = fileName.lastIndexOf("/") + 1;
				fileName = fileName.substring(0, lastSlash) + fileName.substring(lastSlash + 2);
			}
			
			File file = new File(uploadPath + fileName);
			if(!file.exists()) {
				file = new File(uploadDirectPath + fileName);
			}
			file.delete();
			
			return new ResponseEntity<>("deleted", HttpStatus.OK);
		}catch(Exception e) {
			logger.debug(e.getMessage());
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
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
		
		String savedFileName = FileUtils.uploadFile(file, uploadPath);
		model.addAttribute("savedFileName", savedFileName);
		model.addAttribute("type", type);
	}
}
