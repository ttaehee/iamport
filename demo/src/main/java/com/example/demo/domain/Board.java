package com.example.demo.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Board {
	
	private int boardNo;
	private String title;
	private String content;
	private String regDate;
	private Date upDate;

}
