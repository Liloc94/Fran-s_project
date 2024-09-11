package com.korea.project.service.admin;

import com.korea.project.vo.franchise.FranchiseVO;

import java.util.List;

public interface AdminFranchiseService {

    FranchiseVO getFranchiseById(int id);

    void addFranchise(FranchiseVO franchise);

    void updateFranchise(FranchiseVO franchise);

    void deleteFranchise(int id);
    
    List<FranchiseVO> searchFranchisesByName(String name,int offset, int limit);

    int countAllFranchises();
    int countFranchisesByName(String name);

	List<FranchiseVO> selectAllFranchisesPaged(int offset, int pageSize);
	List<FranchiseVO> getFranchisesBySectorPaged(int sector, int offset, int limit);
	int countFranchisesBySector(int sector);

}
