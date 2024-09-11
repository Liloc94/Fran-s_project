package com.korea.project.service.board;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.korea.project.dto.board.BoardListRequest;
import com.korea.project.dto.board.BoardResponse;
import com.korea.project.dto.board.PagingResponse;
import com.korea.project.vo.board.BoardVO;


public interface BoardService {
	
	
	
	//게시글 조회 
	public PagingResponse<BoardResponse> findBoardList(final BoardListRequest params);
	//public List<BoardVO> getList();
	
	//게시글 조회수 증가
	public int viewCount(int boardIdx);
	
	
	//게시글 추가
    public void register(BoardVO boardVO);
	
//	//게시글 카테고리, 검색어에 관한 필터링 조회 
//	public List<BoardListResponse> filter(BoardListRequest boardListRequest);
	
    //게시글 상세페이지
    //특정 게시글의 상세점보를 담은 게시글의 응답 개체
    //params id = PK
    //@return = 게시글 상세정보
//	public PagingResponse<BoardVO> findBoardPost(final BoardListRequest params);
	
    
    //게시글 삭제하기
    public int delPost(int boardIdx);
    
    
    //게시글 수정하기
    public int updateBoard(final BoardResponse boardResponse);
    
    //게시글 상세조회
	public BoardResponse findById(int boardIdx);
	
	
}
