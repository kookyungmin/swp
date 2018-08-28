package com.gguri.swp.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gguri.swp.domain.BoardVO;
import com.gguri.swp.domain.Criteria;
import com.gguri.swp.domain.PageMaker;
import com.gguri.swp.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Inject
	private BoardService service;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(BoardVO board, 
					 		@ModelAttribute("cri") Criteria cri,
					 		Model model) throws Exception{
		logger.info("register get.....");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(BoardVO board,
							   Criteria cri,
							   RedirectAttributes rttr) throws Exception{
		logger.info("register post.....");
		logger.info(board.toString());
		
		service.regist(board);
		
		rttr.addFlashAttribute("result", "registerOK");
		rttr.addAttribute("page", 1);
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		
		return "redirect:/board/listPage";
	}
	
	
	@RequestMapping(value = "/dummy")
	public String dummyPOST(RedirectAttributes rttr) throws Exception{
		logger.info("dummy post.....");
		
		service.dummy();
		
		rttr.addFlashAttribute("result", "registerOK");
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("bno") Integer bno, 
					 @ModelAttribute("cri") Criteria cri,
					 HttpServletResponse response,
					 Model model) throws Exception{
		logger.info("read GET...");
		BoardVO board = service.read(bno);
		if(board == null) {
			response.sendError(404);
		}
		model.addAttribute(board);
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public void updateGET(@RequestParam("bno") Integer bno, 
						  @ModelAttribute("cri") Criteria cri, 
						  Model model) throws Exception{
		logger.info("update GET");
		BoardVO board = service.read(bno);
		model.addAttribute(board);
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updatePOST(BoardVO board, 
							 Criteria cri,
							 RedirectAttributes rttr) throws Exception{
		logger.info("update POST");
		service.modify(board);
		rttr.addFlashAttribute("result","saveOK");
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("bno", board.getBno());
		
		return "redirect:/board/read";
	}
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String remove(@RequestParam("bno") Integer bno, 
					 Criteria cri,
					 RedirectAttributes rttr) throws Exception{
		logger.info("remove");
		service.remove(bno);
		rttr.addFlashAttribute("result","removeOK");
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/board/listPage";
	}

	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void listPage(Criteria cri, Model model) throws Exception{
		logger.info("listPage");
		List<BoardVO> boards = service.listPage(cri);
		model.addAttribute("list",boards);
		PageMaker pageMaker = new PageMaker(cri);
		int totalCount = service.getTotalCount(cri);
		pageMaker.setTotalCount(totalCount);
		model.addAttribute("pageMaker", pageMaker);
	}
}
