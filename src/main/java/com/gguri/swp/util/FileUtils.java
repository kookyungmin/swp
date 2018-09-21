package com.gguri.swp.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtils {
	
	private static Map<String, MediaType> mediaMap;
	static {
		mediaMap = new HashMap<>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	//원본 파일의 이름과 파일 데이터를 byte[]로 변환한 정보를 파라미터로 처리해서 실제로 업로딩
	public static String uploadFile(MultipartFile file, String uploadPath) throws Exception{
		//파일이름을 중복되지 않는 값으로 변경
		String fileName = UUID.randomUUID().toString() 
				+ "_" 
				+ file.getOriginalFilename();
		
		//파일이 저장될 경로에 날짜 적용
		String dirName = getCurrentUploadPath(uploadPath);
		
		//파일 저장되는 경로에 파일 생성 후
		File target = new File(dirName, fileName);
		
		//바이트 파일 복사
		FileCopyUtils.copy(file.getBytes(), target);
		
		String ext = getFileExtension(fileName);
		String uploadedFileName = null;
		//파일이 이미지라면 썸네일 이미지 생성
		if (getMediaType(ext) != null) {
			uploadedFileName = makeThumbnail(uploadPath, dirName, fileName);
		}else {
			uploadedFileName = makeIcon(uploadPath, dirName, fileName);
		}
		return uploadedFileName;
	}

	/**
	 * 
	 * uploadRootPath = uploadPath
	 * dirName = dirName = uploadPath\2018\09\20
	 * fileName = abc.ppt
	 * 
	 */
	
	private static String makeIcon(String uploadRootPath, String dirName, String fileName) throws Exception{
		//uploadPath\2018\09\20\abc.ppt
		String iconName = dirName + File.separator + fileName;
		//uploadPath\2018\09\20\abc.ppt -> /2018/09/20/abc.ppt
		return iconName.substring(uploadRootPath.length()).replace(File.separatorChar, '/');
	}

	/**
	 * uploadRootPath = uploadPath
	 * dirName = uploadPath\2018\09\20
	 * fileName = abc.b.jpg
	 * 
	 */
	public static String makeThumbnail(String uploadRootPath, String dirName, String fileName) throws IOException {
		BufferedImage srcImg = ImageIO.read(new File(dirName, fileName));
		BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC,
				Scalr.Mode.FIT_TO_HEIGHT, 100);
		// uploadPath\2018\09\20\s_abc.b.jpg
		String thumbnailName = dirName + File.separator + "s_" + fileName;
		//s_abc.b.jpg 생성
		File newFile = new File(thumbnailName);
		//jpg
		String ext = getFileExtension(fileName);
		//썸네일 이미지를 s_abc.b.jpg에 덮어씀
		ImageIO.write(destImg, ext.toUpperCase(), newFile);
		
		// 브라우저에서 윈도우의 경로로 사용하는 \ 문자가 정상적인 경로로 인식되지 않기 때문
		// ex) <img src="/2018/09/20/s_abc.b.jpg"> 와 같이 사용
		// \2018\09\20\s_abc.b.jpg -> /2018/09/20/s_abc.b.jpg
		return thumbnailName.substring(uploadRootPath.length()).replace(File.separatorChar, '/');
	}

	public static String getCurrentUploadPath(String uploadRootPath) throws Exception{
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH)+1; //월은 0~11
		int d = cal.get(Calendar.DATE);
		
		//디렉토리 만들어줌
		return makeDir(uploadRootPath, "" + y, StringUtils.len2(m), StringUtils.len2(d));
	}
	
	/**
	 * uploadpath\2018 디렉토리 없으면 만듬
	 * uploadpath\2018\09 디렉토리 없으면 만듬
	 * uploadpath\2018\09\20 디렉토리 없으면만듬
	 *
	 * uploadpath\2018\09\20 path 반환
	 */
	
	public static String makeDir(String uploadRootPath, String... paths) throws Exception{
		
		for(String path : paths) {
			//separator OS에 맞게 '\' 를 붙여줌
			uploadRootPath += File.separator + path;
			File tmpFile = new File(uploadRootPath);
			if (tmpFile.exists()) {
				continue;
			}else {
				tmpFile.mkdir();
			}
		}
		return uploadRootPath;
	}
	
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	
	public static MediaType getMediaType(String ext) {
		return mediaMap.get(ext.toUpperCase());
	}
	
}
