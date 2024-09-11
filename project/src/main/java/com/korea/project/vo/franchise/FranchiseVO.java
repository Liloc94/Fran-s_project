package com.korea.project.vo.franchise;

import lombok.Data;

@Data
public class FranchiseVO {
    private String franchiseTitle;
    private String franchiseImg;
    private int franchiseIdx;
    private int franchiseSectors;
    private int franchiseStores;
    private int franchiseAvgSales;
    private int franchiseStartupCost;
}
