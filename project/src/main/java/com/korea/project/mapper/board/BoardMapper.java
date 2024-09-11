package com.korea.project.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.korea.project.dto.board.BoardListRequest;
import com.korea.project.dto.board.BoardResponse;
import com.korea.project.vo.board.BoardVO;

@Mapper
public interface BoardMapper {

	//게시판 조회
	public List<BoardResponse> findAll(BoardListRequest params);
//    List<BoardVO> selectAll();

	
	//게시글 수 카운팅
	int boardCount(BoardListRequest params);
	
	//게시글 조회수 카운팅
	public int viewCount(int boardIdx);

	//게시글 목록 추가
    void insert(BoardVO boardVO);
	
//	//카테고리 필터링 된 게시글 목록 가져오기
//	List<BoardListResponse> filter(BoardListRequest boardListRequest);
	
	//게시글 페이징
	public int count(BoardListRequest boardListRequest);
	
	//게시글 삭제
	public int delPost(int boardIdx);
	
	//게시글 수정
	public int updateBoard(BoardResponse boardResponse);
	
	//게시글 조회
	public BoardResponse findById(int boardIdx);


}
