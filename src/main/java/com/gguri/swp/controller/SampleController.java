package com.gguri.swp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gguri.swp.domain.BoardVO;

@RestController
@RequestMapping("/sample")
public class SampleController {
	@RequestMapping("/hello")
	public String sayHello() {
		return "Hello world";
	}
	
	@RequestMapping("/sendVO")
	public BoardVO sendVO() {
		BoardVO board = new BoardVO();
		board.setBno(1);
		board.setContent("객체를 json 데이터로 반환");
		board.setTitle("연습");
		board.setWriter("꾸리");
		return board;
	}
	@RequestMapping("/sendList")
	public ResponseEntity<List<BoardVO>> sendList(){
		List<BoardVO> list = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			BoardVO board = new BoardVO();
			board.setBno(i);
			board.setContent("객체를 json 데이터로 반환");
			board.setTitle("연습");
			board.setWriter("꾸리");
			list.add(board);
		}
		return new ResponseEntity<>(list,HttpStatus.NOT_FOUND);
	}
}
