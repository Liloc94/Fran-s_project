package com.korea.project.dto.board;

import lombok.Data;
import lombok.Getter;

@Data
public class Pagination {
	private int totalRecordCount, //전체 데이터 수
				totalPageCount,   //전체 페이지 수
				startPage,		  //첫 페이지 번호
				endPage,		  //끝 페이지 번호
				limitStart,		  //limit시작 위치 
				boardCategory, //게시판 종류
				nowPage;
	private boolean existPrevPage,//이전 페이지 존재 여부
				    existNextPage;//다음 페이지 존재 여부
	
	
	//전체 데이터 수 계산하는 pagination
	public Pagination(int totalRecordCount, BoardListRequest params) {
		if(totalRecordCount > 0) {
			this.totalRecordCount = totalRecordCount;
			this.calculation(params); //전체 데이터 수를 BoardListRequest에서 가져와 계산
		}
	}
	
	//페이지 수, 번호, 위치 계산하는 기능 만들어 놓음
	private void calculation(BoardListRequest params) {
		
		nowPage = params.getNowpage();
		//전체 페이지 수 계산
		totalPageCount = ((totalRecordCount -1) / params.getRecordSize()) + 1;
	//	System.out.println("totalPageCount : " +totalPageCount);
		//현재 페이지 번호가 전체 페이지 수 보다 큰 경우, 현재 페이지 번호에 전체 페이지 수 저장
		System.out.println("바꾸기 전 nowPage : " +params.getNowpage());
		
		if(params.getNowpage() > totalPageCount) {
			params.setNowpage(totalPageCount);
		}
		System.out.println("바꾼후 nowPage " +params.getNowpage());
//		
		//첫번째 페이지 번호 계산
		startPage = ((params.getNowpage() - 1) / params.getPageSize()) * params.getPageSize() + 1;
		
		//끝 페이지 번호 계산
		endPage = startPage + params.getPageSize() -1; 
		
		//끝 페이지가 전체 페이지 수 보다 큰 경우, 페이지 전체 페이지 수 저장
		if(endPage > totalPageCount) {
			endPage = totalPageCount;
		}
		System.out.println("너야 ?nowPage : " +params.getNowpage() );
		//Limit 시작 위치 계산 
		limitStart = (params.getNowpage() - 1) * params.getRecordSize();
		
		//이전 페이지 존재 여부 확인
		existPrevPage = startPage != 1;
		
		//다음 페이지 존재 여부 확인
		existNextPage = (endPage * params.getRecordSize()) < totalRecordCount;
	}
}
