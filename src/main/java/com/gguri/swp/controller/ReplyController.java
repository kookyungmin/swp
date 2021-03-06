package com.gguri.swp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.PageMaker;
import com.gguri.swp.domain.ReplyVO;
import com.gguri.swp.domain.UserVO;
import com.gguri.swp.interceptor.SessionNames;
import com.gguri.swp.service.ReplyService;

@RestController
@RequestMapping("/replies")
public class ReplyController {
	private static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@Inject
	private ReplyService service;
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<String> register(@RequestBody ReplyVO reply){
		logger.debug("ReplyRegister>>{}",reply);
		try {
			service.register(reply);
			return new ResponseEntity<>("ReplyRegisterOK", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{rno}", method = {RequestMethod.PUT, RequestMethod.PATCH})
	public ResponseEntity<String> update(@PathVariable("rno") Integer rno,
									  	 @RequestBody ReplyVO reply){
		logger.debug("ReplyUpdate>>{}",rno, reply);
		try {
			reply.setRno(rno);
			service.modify(reply);
			return new ResponseEntity<>("ReplyUpdateOK", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{rno}", method = RequestMethod.GET)
	public ResponseEntity<ReplyVO> read(@PathVariable("rno") Integer rno){
		logger.debug("ReplyRead>>{}", rno);
		try {
			ReplyVO reply = service.read(rno);
			logger.debug("ReplyRegister>>{}", reply.toString());
			return new ResponseEntity<>(reply, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{rno}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("rno") Integer rno){
		logger.debug("ReplyDelete>>{}", rno);
		try {
			service.remove(rno);
			return new ResponseEntity<>("ReplyDeleteOK", HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value = "/all/{bno}/{page}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> listPage(@PathVariable("bno") Integer bno,
														@PathVariable("page") Integer page,
														HttpSession session){
		logger.debug("ReplyList>>{}", bno);
		try {
			Map<String, Object> map = new HashMap<>();
			Criteria cri = new Criteria();
			cri.setPage(page);
			PageMaker pagemaker = new PageMaker(cri);
			List<ReplyVO> list = service.listReplyPage(bno, cri);
			map.put("list", list);
			
			int replyCount = service.getTotalCount(bno);
			pagemaker.setTotalCount(replyCount);
			
			map.put("pageMaker", pagemaker);
			UserVO loginUser = (UserVO)session.getAttribute(SessionNames.LOGIN);
			if(loginUser != null) {
				map.put("loginUid", loginUser.getUid());
			}
			
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
