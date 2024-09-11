package com.korea.project.service.board;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.korea.project.dao.board.BoardDAO;
import com.korea.project.dto.board.BoardListRequest;
import com.korea.project.dto.board.BoardResponse;
import com.korea.project.dto.board.Pagination;
import com.korea.project.dto.board.PagingResponse;
import com.korea.project.dto.user.SessionUserDTO;
import com.korea.project.mapper.board.BoardMapper;
import com.korea.project.vo.board.BoardVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

	private final BoardDAO boardDAO;
	private final HttpSession session;
	
	//게시글 조회
	@Override
	public PagingResponse<BoardResponse> findBoardList(BoardListRequest params) {
		
		
		//조건에 해당하는 데이터가 없는 경우에 응답 데이터에 비어있는 리스트와 null을 담아 반환
		System.out.println("서비스 들어오자마자 nowPage :" + params.getNowpage());
		int count = boardDAO.count(params);
		System.out.println("count : " +count);
		if(count < 1) {
			System.out.println("얜가?");
			return new PagingResponse<>(Collections.emptyList(), null);
		}
		
		//Pagination 객체를 생성해서 페이지 정보 계산 후 params에 계산된 페이지 정보 
		System.out.println(params);
		Pagination pagination = new Pagination(count, params);
		System.out.println("pagination :" +pagination);
		params.setPagination(pagination);
		
		System.out.println("서비스에서 파람"+params);
		
		
		//계산된 페이지 정보의 일부(limitStart, recordSize)를 기준으로 리스트 데이터 조회 후 반환
		List<BoardResponse> list = boardDAO.findBoardList(params);
		System.out.println(list);
		return new PagingResponse<>(list, pagination);
	}
	
	//게시글 조회수 카운팅
	@Override
	public int viewCount(int boardIdx) {
		return boardDAO.viewCount(boardIdx);
	}

	
//	@Override
//	public List<BoardVO> getList() {
//		return boardDAO.findAll();
//	}
	
	//게시글 등록
	@Override
	public void register(BoardVO boardVO) {
		SessionUserDTO user=  (SessionUserDTO)session.getAttribute("user");
		boardVO.setUserIdx(user.getUserIdx()); ;
		boardDAO.save(boardVO);
		
	}
	
//	//게시글 카테고리, 검색어에 대한 필터링 조회
//	@Override
//	public List<BoardListResponse> filter(BoardListRequest boardListRequest) {
//		return boardDAO.filter(boardListRequest);
//	}
	
	//게시글 삭제
	@Override
	public int delPost(int boardIdx) {
		return boardDAO.delPost(boardIdx);
	}
	
	//게시글 수정
	@Override
	public int updateBoard(BoardResponse boardResponse) {
		boardDAO.update(boardResponse);
		return boardResponse.getUserIdx();
	}
	
	//게시글 상세 조회
	@Override
	public BoardResponse findById(int boardIdx) {
		return boardDAO.findById(boardIdx);
	}
	
//	@Override
//	public PagingResponse<BoardVO> findBoardPost(BoardListRequest params) {
//		int count = boardDAO.boardCount(params);
//		if(count < 1) {
//			return new PagingResponse<>(Collections.emptyList(), null);
//		}
//		
//		Pagination pagination = new Pagination(count, params);
//		params.setPagination(pagination);
//		
//		List<BoardVO> list = boardDAO.findBoardList(params);
//		return new PagingResponse<>(list, pagination);
//	}
	
	

}
