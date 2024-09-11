package com.korea.project.service.franchise;

import com.korea.project.vo.franchise.FranchiseVO;

import java.util.List;

public interface FranchiseService {
    
    List<FranchiseVO> getAllFranchisesPaged(int offset, int limit);

    List<FranchiseVO> getFranchisesBySectorPaged(int sector, int offset, int limit);

    List<FranchiseVO> getAllFranchises();

    int countAllFranchises();

    int countFranchisesBySector(int sector);

    List<FranchiseVO> searchFranchisesByName(String name, int offset, int limit);

    int countFranchisesByName(String name);

    FranchiseVO getFranchiseById(int id);

}
