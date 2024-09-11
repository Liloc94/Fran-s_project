package com.korea.project.dto.board;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;

@Data
public class PagingResponse<T> {
	private List<T> list = new ArrayList<>();
	private Pagination pagination;
	
	
	public PagingResponse(List<T> list, Pagination pagination) {
		this.list.addAll(list); //List<T>의 모든 데이터를 받아온다
		this.pagination = pagination;
	}
}
