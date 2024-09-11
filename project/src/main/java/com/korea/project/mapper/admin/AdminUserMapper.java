package com.korea.project.mapper.admin;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.korea.project.vo.user.UserVO;

@Mapper
public interface AdminUserMapper {
    List<UserVO> selectUser();
    int updateUser(int userIdx);
    List<UserVO> selectUserByPage(@Param("offset") int offset, @Param("limit") int limit);
    int countAllUsers();
    List<UserVO> selectUserByName(@Param("name") String name,@Param("offset") int offset, @Param("limit") int limit);
    int countUserByName(String name);
}
