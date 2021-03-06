package com.gguri.swp.controller;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gguri.swp.domain.MessageVO;
import com.gguri.swp.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {
	@Inject
	MessageService service;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> addMessage(@RequestBody MessageVO message){
		ResponseEntity<String> entity = null;
		try {
			service.addMessage(message);
			entity = new ResponseEntity<>("SUCCESS ADD MSG",HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	@RequestMapping(value = "/{uid}/{mno}", method = RequestMethod.GET)
	public ResponseEntity<MessageVO> readMessage(@PathVariable String uid,
											     @PathVariable Integer mno){
		try {
			MessageVO message = service.readMessage(uid, mno);
			return new ResponseEntity<>(message ,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
