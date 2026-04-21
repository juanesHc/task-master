package com.taskmaster.dto.ranking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankingComparisonDto {

    private MonthlyRankingDto currentMonth;
    private MonthlyRankingDto personalBest;
    private double deltaVsBest;
    private double averageHistoricalRate;
    private List<MonthlyRankingDto> history;
}
