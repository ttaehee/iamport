package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.domain.Board;
import com.example.demo.service.BoardService;

@Controller
@RequestMapping("/board/")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	/*
	@RequestMapping(value="insertBoard", method=RequestMethod.GET)
	public String inserttBoard() {
		return "insertBoard";
	}
	
	@RequestMapping(value="insertBoard", method=RequestMethod.POST)
	public String inserttBoard(@ModelAndAttribute("Board") Borad board) {
	    boardService.insertBoard(board);
		return "listBoard";
	}
	
	@RequestMapping("/board")
	public ModelAndView getListBoard() {
		List<Board> board=boardService.getListBoard();
		return new ModelAndView("listBoard","list",board);
	}
	*/
	
	@RequestMapping("/board")
	public String getListBoard() {
		return "listBoard";
	}

}
