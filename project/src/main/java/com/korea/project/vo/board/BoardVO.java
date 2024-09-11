package com.korea.project.vo.board;

import lombok.Data;

@Data
public class BoardVO {
	private int id,//PK
				boardIdx, //게시글 번호
				userIdx , //작성한 사람 번호
				boardSectors,//업종
				boardCategory, // 게시판 종류(상권분석, 창업후기) 
				boardView, // 조회수
				boardDel, //삭제 여부
				step, //댓글 순번
				depth, //계층
				ref; //게시글 번호
	private String 	boardBigArea, // 카테고리-시,도
					boardSmallArea,// 카테고리 - 시, 군, 구
					boardTitle, //게시글 제목
					boardContent, //게시글 내용
					boardWriteDate; //게시글 작성 날짜
				
}
