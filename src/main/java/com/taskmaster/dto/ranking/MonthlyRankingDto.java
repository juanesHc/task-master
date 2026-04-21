package com.taskmaster.dto.ranking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyRankingDto {

    private String yearMonth;
    private int totalTasks;
    private int completedTasks;
    private int missedTasks;
    private double completionRate;
}
