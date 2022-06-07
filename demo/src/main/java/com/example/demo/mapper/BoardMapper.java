package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Board;

@Mapper
public interface BoardMapper {
	
	//게시물 개수
	public int getBoardCount();
	
	//게시글 작성
	public int insertBoard(Board board);
	
	//게시물 목록
    public List<Board> getListBoard();
		
	//게시글 상세
	public Board getBoard(int boardNo);	
	
	//게시글 수정
	public int updateBoard(Board board);
	
	//게시글 삭제
	public int deleteBoard(Board board);

}
