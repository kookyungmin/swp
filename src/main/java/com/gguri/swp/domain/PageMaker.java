package com.gguri.swp.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.gguri.swp.controller.BoardController;

public class PageMaker {
	private int displayPageCnt = 10; // 화면에 보여질 페이지 번호 수
	private int totalCount; // 실제 게시물 수
	private int startPage; // 현재 페이지 기준 맨 처음 페이지 번호 (예: 현재페이지가 13이면 11)
	private int endPage; // 현재 페이지 기준 맨 끝 페이지 번호 (예: 현재페이지가 13 이면 20)
	private boolean prev; // 이전 버튼 보여질 것인지 아닌지 판단
	private boolean next; // 다음 버튼 보여질 것인지 아닌지 판단
	private Criteria cri;
	
	public PageMaker(Criteria cri) {
		this.cri = cri;
	}
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		
		calcData(); // totalcount가 넘어오면 startpage, endpage, prev, next 를 계산한다.
	}
	
	public void calcData() {
		int page = this.cri.getPage();
		int perPageNum = this.cri.getPerPageNum();
		//예: 현재 페이지가 13이면 13/10 = 1.3 올림-> 2 끝페이지는 2*10=20
		this.endPage = (int)(Math.ceil(page/(double)displayPageCnt)*displayPageCnt);
		//예: 현재 페이지가 13이면 20-10 +1 = 11 
		this.startPage = (this.endPage-displayPageCnt) + 1;
		//예: 전체 게시물 수가 88개이면 88/10=8.8 올림-> 9
		int tempEndPage = (int)(Math.ceil(totalCount / (double) perPageNum));
		
		//만약 보여질 페이지 번호 수보다 실제 사용될 페이지 번호 수가 작으면 실제 사용될 페이지 번호만 보여줌
		if(this.endPage > tempEndPage) {
			this.endPage = tempEndPage;
		}
		
		this.prev = (startPage != 1); // startPage 1이 아니면 false
		this.next = (endPage * perPageNum < totalCount); //아직 더 보여질 페이지가 있으면 true 
	}
	
	public String makeQuery(int page) {
		return makeQuery(page,true);
	}
	
	public String makeQuery(int page, boolean needSearch) {
		UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance()
				.queryParam("page", page)
				.queryParam("perPageNum", this.cri.getPerPageNum());
				
		if (needSearch) {
			uriComponentsBuilder
					.queryParam("searchType", this.cri.getSearchType())
					.queryParam("keyword", this.cri.getKeyword());
		}
		return uriComponentsBuilder.build().encode().toString();
	}
	
	
	public int getDisplayPageCnt() {
		return displayPageCnt;
	}

	public void setDisplayPageCnt(int displayPageCnt) {
		this.displayPageCnt = displayPageCnt;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	public int getTotalCount() {
		return totalCount;
	}

	

	
}
