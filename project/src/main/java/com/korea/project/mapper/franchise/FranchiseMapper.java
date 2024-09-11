package com.korea.project.mapper.franchise;

import com.korea.project.vo.franchise.FranchiseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FranchiseMapper {
    List<FranchiseVO> selectAllFranchisesPaged(@Param("offset") int offset, @Param("limit") int limit);

    List<FranchiseVO> selectFranchisesBySectorPaged(@Param("sector") int sector, @Param("offset") int offset, @Param("limit") int limit);

    List<FranchiseVO> selectAllFranchises();

    List<FranchiseVO> selectFranchisesBySector(int sector);

    int countAllFranchises();

    int countFranchisesBySector(@Param("sector") int sector);
    
    // 검색창
    List<FranchiseVO> searchFranchisesByName(@Param("name") String name, @Param("offset") int offset, @Param("limit") int limit);
    
    int countFranchisesByName(@Param("name") String name);
    
    // 상세창 받아오기
    FranchiseVO selectFranchiseById(@Param("id") int id);
    
    //프렌차이즈 생성
    void insertFranchise(FranchiseVO franchise);
    // 프렌차이즈 수정
    void updateFranchise(FranchiseVO franchise);
    // 프렌차이즈 삭제
    void deleteFranchise(int id);
}
