package com.gguri.swp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Calendar;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gguri.swp.util.FileUtils;
import com.gguri.swp.util.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/**/root-context.xml")
public class UtilTest {
	private static Logger logger = LoggerFactory.getLogger(UtilTest.class);
	
	@Test
	public void makeThumbnailTest() throws Exception{
		String uploadRootPath = "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\uploads";
		assertTrue(existsDir(uploadRootPath));
		String dirName = "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\uploads\\2018\\09\\21";
		String fileName = "test.jpg";
		String thumbnailName = dirName + File.separator + "s_" + fileName;
		
		File old =new File(thumbnailName);
		if(old.exists())
			old.delete();
		
		String makeThumbnail = FileUtils.makeThumbnail(uploadRootPath, dirName, fileName);
		assertTrue(new File(thumbnailName).exists());
		assertEquals(makeThumbnail,"/2018/09/21/s_test.jpg");
	}
	
	@Ignore
	public void getCurrentUploadPathTest() throws Exception{
		String uploadRootPath = "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\uploads";
		assertTrue(existsDir(uploadRootPath));
		
		String path = FileUtils.getCurrentUploadPath(uploadRootPath);
		logger.debug("path={}",path);
		assertTrue(existsDir(path));
	}
	
	
	@Ignore
	public void test() throws Exception {
		String uploadRootPath = "C:\\Users\\Administrator\\Documents\\workspace-sts-3.9.5.RELEASE\\uploads";
		assertTrue(existsDir(uploadRootPath));
		
		Calendar cal = Calendar.getInstance();
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH)+1; //월은 0~11
		int d = cal.get(Calendar.DATE);
		
		assertEquals(StringUtils.len2(9), "09");
		String currentPath = FileUtils.makeDir(uploadRootPath, 
					y + "", StringUtils.len2(m), StringUtils.len2(d));
		assertEquals(currentPath, uploadRootPath 
				+ File.separator + y 
				+ File.separator + StringUtils.len2(m)
				+ File.separator + StringUtils.len2(d));
		
		String path = uploadRootPath + File.separator + y;
		assertTrue(existsDir(path));
		path += File.separator + StringUtils.len2(m);
		assertTrue(existsDir(path));
		path += File.separator + StringUtils.len2(d);
		assertTrue(existsDir(path));
	}
	private boolean existsDir(String path) {
		return new File(path).exists();
	}
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
}
