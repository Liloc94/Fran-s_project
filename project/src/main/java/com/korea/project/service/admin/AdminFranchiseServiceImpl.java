package com.korea.project.service.admin;

import com.korea.project.mapper.franchise.FranchiseMapper;
import com.korea.project.vo.franchise.FranchiseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminFranchiseServiceImpl implements AdminFranchiseService {

    private final FranchiseMapper franchiseMapper;

    @Autowired
    public AdminFranchiseServiceImpl(FranchiseMapper franchiseMapper) {
        this.franchiseMapper = franchiseMapper;
    }

    @Override
    public FranchiseVO getFranchiseById(int id) {
        return franchiseMapper.selectFranchiseById(id);
    }

    @Override
    public void addFranchise(FranchiseVO franchise) {
        franchiseMapper.insertFranchise(franchise);
    }

    @Override
    public void updateFranchise(FranchiseVO franchise) {
        franchiseMapper.updateFranchise(franchise);
    }

    @Override
    public void deleteFranchise(int id) {
        franchiseMapper.deleteFranchise(id);
    }

    @Override
    public List<FranchiseVO> searchFranchisesByName(String name, int offset, int limit) {
        return franchiseMapper.searchFranchisesByName(name, offset, limit);
    }

    @Override
    public int countAllFranchises() {
        return franchiseMapper.countAllFranchises();
    }

    @Override
    public int countFranchisesByName(String name) {
        return franchiseMapper.countFranchisesByName(name);
    }

    @Override
    public List<FranchiseVO> selectAllFranchisesPaged(int offset, int pageSize) {
        return franchiseMapper.selectAllFranchisesPaged(offset, pageSize);
    }

    @Override
    public List<FranchiseVO> getFranchisesBySectorPaged(int sector, int offset, int limit) {
        return franchiseMapper.selectFranchisesBySectorPaged(sector, offset, limit);
    }

    @Override
    public int countFranchisesBySector(int sector) {
        return franchiseMapper.countFranchisesBySector(sector);
    }
}