package com.korea.project.dto.user;

import com.korea.project.dto.board.Pagination;

import lombok.Data;
@Data
public class MyPostRequestDTO {
	private String 	boardBigArea, // 카테고리-시,도
	boardSmallArea,// 카테고리 - 시, 군, 구
	searchKeyword; //검색어 키워드

	private int boardSectors, //업종
				nowpage, //현재 페이지 번호
				pageSize, //화면 하단에 출력할 페이지 사이즈 
				userIdx; // 세션에서 넘겨준 userIdx
	final private int recordSize; //페이지당 출력할 목록 개수, 시작위치(offset)을 기준으로 조회할 데이터의 개수
	
	private Pagination pagination; //페이지네이션 -> 클래스를 따로 만들어서 기능들을 갖다쓴다
			



	public MyPostRequestDTO() {
		this.nowpage = 1; //현재 페이지는 1
		this.recordSize = 10; //한 페이지당 글의 수는 10개씩 보여주기
		this.pageSize = 10; //페이지 수는 10단위 ex)1-10, 11-20, 21-30...
	}

}
