package com.korea.project.service.franchise;

import com.korea.project.mapper.franchise.FranchiseMapper;
import com.korea.project.vo.franchise.FranchiseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FranchiseServiceImpl implements FranchiseService {

    private final FranchiseMapper franchiseMapper;

    @Autowired
    public FranchiseServiceImpl(FranchiseMapper franchiseMapper) {
        this.franchiseMapper = franchiseMapper;
    }

    @Override
    public List<FranchiseVO> getAllFranchisesPaged(int offset, int limit) {
        try {
            return franchiseMapper.selectAllFranchisesPaged(offset, limit);
        } catch (DataAccessException e) {
            handleDataAccessException("Failed to retrieve franchises", e);
            throw e;
        }
    }

    @Override
    public List<FranchiseVO> getFranchisesBySectorPaged(int sector, int offset, int limit) {
        try {
            return franchiseMapper.selectFranchisesBySectorPaged(sector, offset, limit);
        } catch (DataAccessException e) {
            handleDataAccessException("Failed to retrieve franchises for sector " + sector, e);
            throw e;
        }
    }

    @Override
    public List<FranchiseVO> getAllFranchises() {
        try {
            return franchiseMapper.selectAllFranchises();
        } catch (DataAccessException e) {
            handleDataAccessException("Failed to retrieve all franchises", e);
            throw e;
        }
    }

    @Override
    public int countAllFranchises() {
        try {
            return franchiseMapper.countAllFranchises();
        } catch (DataAccessException e) {
            handleDataAccessException("Failed to count all franchises", e);
            throw e;
        }
    }

    @Override
    public int countFranchisesBySector(int sector) {
        try {
            return franchiseMapper.countFranchisesBySector(sector);
        } catch (DataAccessException e) {
            handleDataAccessException("Failed to count franchises for sector " + sector, e);
            throw e;
        }
    }

    @Override
    public List<FranchiseVO> searchFranchisesByName(String name, int offset, int limit) {
        return franchiseMapper.searchFranchisesByName(name, offset, limit);
    }

    @Override
    public int countFranchisesByName(String name) {
        return franchiseMapper.countFranchisesByName(name);
    }

    @Override
    public FranchiseVO getFranchiseById(int id) {
        try {
            return franchiseMapper.selectFranchiseById(id);
        } catch (DataAccessException e) {
            handleDataAccessException("Failed to retrieve franchise with id " + id, e);
            throw e;
        }
    }

    private void handleDataAccessException(String message, DataAccessException e) {
        System.err.println(message + ": " + e.getMessage());
    }
}
