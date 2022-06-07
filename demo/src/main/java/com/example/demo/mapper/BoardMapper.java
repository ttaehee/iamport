package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.Board;

@Mapper
public interface BoardMapper {
	
	//�Խù� ����
	public int getBoardCount();
	
	//�Խñ� �ۼ�
	public int insertBoard(Board board);
	
	//�Խù� ���
    public List<Board> getListBoard();
		
	//�Խñ� ��
	public Board getBoard(int boardNo);	
	
	//�Խñ� ����
	public int updateBoard(Board board);
	
	//�Խñ� ����
	public int deleteBoard(Board board);

}
