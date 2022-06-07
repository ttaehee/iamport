package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.domain.User;

@Mapper
public interface UserMapper {
	
	@Select("SELECT TO_CHAR(SYSDATE, 'yyyy-mm-dd') FROM DUAL")
	public String selectNow();
	
	public List<User> selectUserList();

}
