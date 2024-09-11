package com.korea.project.dto.board;

import lombok.Data;

@Data
public class BoardResponse {

		private int boardView,
		 			userIdx,
					boardCategory;
		private String boardIdx,
					   boardTitle,
					   userNickname,
					   boardWriteDate,
					   boardSectors,
					   boardBigArea, // 카테고리-시,도
					   boardSmallArea,// 카테고리 - 시, 군, 구
					   boardContent;
					  


					   
					
}
