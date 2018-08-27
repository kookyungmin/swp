package com.gguri.swp.controller;

import java.util.List;

import javax.inject.Inject;

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
	public void registerGET(BoardVO board, Model model) throws Exception{
		logger.info("register get.....");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(BoardVO board, RedirectAttributes rttr) throws Exception{
		logger.info("register post.....");
		logger.info(board.toString());
		
		service.regist(board);
		
		rttr.addFlashAttribute("result", "success");
		
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value = "/dummy")
	public String dummyPOST(RedirectAttributes rttr) throws Exception{
		logger.info("dummy post.....");
		
		service.dummy();
		
		rttr.addFlashAttribute("result", "success");
		return "redirect:/board/listPage";
	}
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("bno") Integer bno, 
				@ModelAttribute("cri") Criteria cri,
				Model model) throws Exception{
		logger.info("read GET...");
		BoardVO board = service.read(bno);
		model.addAttribute(board);
	}
	
//	@RequestMapping(value = "/update", method = RequestMethod.GET)
//	public void update(@RequestParam("bno") Integer bno, Model model) throws Exception{
//		logger.info("update GET");
//		BoardVO board = service.read(bno);
//		model.addAttribute(board);
//	}
//	@RequestMapping(value = "/update", method = RequestMethod.POST)
//	public String update(BoardVO board, RedirectAttributes rttr) throws Exception{
//		logger.info("update POST");
//		service.modify(board);
//		rttr.addFlashAttribute("result","saveOK");
//		return "redirect:/board/read?bno="+board.getBno();
//	}
//	@RequestMapping(value = "/remove", method = RequestMethod.GET)
//	public String remove(@RequestParam("bno") Integer bno, RedirectAttributes rttr) throws Exception{
//		logger.info("remove");
//		service.remove(bno);
//		rttr.addFlashAttribute("result","removeOK");
////		return "redirect:/board/listAll";
//		return "redirect:/board/listPage";
//	}
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public void update(@RequestParam("bno") Integer bno, 
				@ModelAttribute("cri") Criteria cri, 
				Model model) throws Exception{
		logger.info("update GET");
		BoardVO board = service.read(bno);
		model.addAttribute(board);
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(BoardVO board, 
				Criteria cri, 
				RedirectAttributes rttr) throws Exception{
		logger.info("update POST");
		service.modify(board);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("pagePerNum", cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		rttr.addAttribute("bno",board.getBno());
		return "redirect:/board/read";
	}
	@RequestMapping(value = "/remove", method = RequestMethod.GET)
	public String remove(@RequestParam("bno") Integer bno, 
				Criteria cri, RedirectAttributes rttr) throws Exception{
		logger.info("remove");
		service.remove(bno);
		rttr.addFlashAttribute("result","removeOK");
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("pagePerNum", cri.getPerPageNum());
		rttr.addAttribute("searchType",cri.getSearchType());
		rttr.addAttribute("keyword",cri.getKeyword());
		return "redirect:/board/listPage";
	}
	
	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public void listAll(Model model) throws Exception{
		logger.info("show all list");
		List<BoardVO> boards = service.listAll();
		model.addAttribute("list",boards);
	}
	
	@RequestMapping(value = "/listPage", method = RequestMethod.GET)
	public void listPage(Criteria cri, Model model) throws Exception{
		logger.info(cri.toString());
		List<BoardVO> boards = service.listCriteria(cri);
		model.addAttribute("list",boards);
		PageMaker pageMaker = new PageMaker(cri);
		int totalCount = service.getTotalCount(cri);
		//@ToDo
		//pageMaker.setTotalCount(113);
		pageMaker.setTotalCount(totalCount);
		model.addAttribute("pageMaker", pageMaker);
	}
}
