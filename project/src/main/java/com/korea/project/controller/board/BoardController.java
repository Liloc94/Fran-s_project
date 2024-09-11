package com.korea.project.controller.board;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.korea.project.dto.board.BoardListRequest;
import com.korea.project.dto.board.BoardResponse;
import com.korea.project.dto.board.PagingResponse;
import com.korea.project.mapper.board.BoardMapper;
import com.korea.project.service.board.BoardService;
import com.korea.project.vo.board.BoardVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board/*")
@Slf4j
public class BoardController {
	final BoardService boardService;
	final private HttpSession session;
	
	//게시글 목록 보여주기
	@GetMapping("list")
	public String list(@ModelAttribute("params") final BoardListRequest params, Model model) {
		System.out.println(params);
		//페이지 수
		int nowPage = 1;
		System.out.println("con nowPage : " + params.getNowpage());
		if(params.getNowpage() != 0) {
			nowPage = params.getNowpage();
		}
		params.setNowpage(nowPage);
		PagingResponse<BoardResponse> response = boardService.findBoardList(params);
		
		System.out.println("짜잔: " + response);
		model.addAttribute("response",response);
		
		
		
		return "board/boardList";
 	}
	
	//게시글 추가하기
	@GetMapping("register")
	public String insert(Model model) {
		
		if(session.getAttribute("user")== null) {
			return "redirect:/access-denied";
		}
		model.addAttribute("vo", new BoardVO());
		
		return "board/boardInsert";
	}
	
	@PostMapping("register")
	public RedirectView insert(BoardVO boardVO) {
	System.out.println(boardVO.getBoardSectors());
		
		log.info("게시글 정보 :" + boardVO);
		
		boardService.register(boardVO);
		
		
		return new RedirectView("list");
	}
	
	//게시글 삭제하기
	@PostMapping("del")
	public String delPost(@RequestParam int boardIdx) {
		log.info("" + boardIdx);
		boardService.delPost(boardIdx);
		return "redirect:/board/list";
	}
	
	//게시글 상세 페이지
	@GetMapping("view")
	public String openBoardView(@RequestParam int boardIdx, Model model) {
		//id는  findBoardById 쿼리의 WHERER조건으로 사용되는 게시글 번호임
		BoardResponse vo = boardService.findById(boardIdx);
//		System.out.println(vo.getBoardIdx());
		model.addAttribute("vo",vo);
		
		//조회수 올려주기
		boardService.viewCount(boardIdx);
		
		return "board/boardView";
	}
	
	//게시글 수정하기
	//수정한 게시글을 보내주기
	@GetMapping("update")
	public String boardUpdate(int boardIdx, Model model) {
		BoardResponse boardResponse = boardService.findById(boardIdx);
		model.addAttribute("vo", boardResponse);
		return "board/boardInsert";
	}
	
	
	//기존 글을 가져오기
	@PostMapping("update")
	public String updateForm(@ModelAttribute BoardResponse boardResponse) {
		System.out.println(boardResponse);
		boardService.updateBoard(boardResponse);		
		return "redirect:/board/view?boardIdx=" + boardResponse.getBoardIdx();
		
	}
	
	//게시글 목록 필터링해서 검색하기
//	@GetMapping("list")
//	@ResponseBody
//	public List<BoardListResponse> filterList(  @RequestParam(required = false) int sector,
//									            @RequestParam(required = false) String bigArea,
//									            @RequestParam(required = false) String smallArea,
//									            @RequestParam(required = false) String keyword){
//		
//		BoardListRequest boardCR = new BoardListRequest();
//	    boardCR.setBoardSectors(sector);
//	    boardCR.setBoardBigArea(bigArea.trim());
//	    boardCR.setBoardSmallArea(smallArea.trim());
//	    boardCR.setSearchKeyword(keyword.trim());
//		System.out.println(boardCR);
//		System.out.println(boardService.filter(boardCR));
//		return boardService.filter(boardCR);
//	}
	
	
	

	
}
