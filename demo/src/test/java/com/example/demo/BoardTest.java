package com.example.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.Board;
import com.example.demo.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BoardTest {
	
	@Autowired
	@Qualifier("boardService")
	private BoardService boardService;
	
	@Test
	public void testInsert() {
		Board board=new Board();
		//board.setBoardNo(43);
		board.setTitle("제목");
		board.setContent("내용");
		
		int result=boardService.insertBoard(board);
		System.out.println("결과 : "+ result);
		
		assertEquals(board.getTitle(),"제목");
	}
	
	/*
	@Test
	void testUPDATE() {
		Board board=new Board();
		
		board.setBoardNo(42);
		board.setTitle("제목수정");
		board.setContent("내용수정");
		
		int result=boardService.updateBoard(board);
		if(result==1) {
			board=boardService.getBoard(42);
			try {
				String boardJson=new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(board);
				System.out.println("===="+boardJson);
			}catch(JsonProcessingException e) {
				e.printStackTrace();
			}
		}
	}
	*/

}
