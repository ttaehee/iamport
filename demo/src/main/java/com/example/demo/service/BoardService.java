package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.domain.Board;
import com.example.demo.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	private BoardMapper boardMapper;
	
	//게시글 개수
	public int getBoardCount() {
		return boardMapper.getBoardCount();
	}
		
	//게시글 작성
	public int insertBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
		
	//게시글 목록
	public List<Board> getListBoard(){
		return boardMapper.getListBoard();
	}
			
	//게시글 상세
	public Board getBoard(int boardNo) {
		return boardMapper.getBoard(boardNo);
	}
		
	//게시글 수정
	public int updateBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
		
	//게시글 삭제
	public int deleteBoard(Board board) {
		return boardMapper.deleteBoard(board);
	}
	

}
