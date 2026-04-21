package com.taskmaster.mapper.ranking;

import com.taskmaster.dto.ranking.MonthlyRankingDto;
import com.taskmaster.entity.MonthlyStatsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RankingMapper {

    MonthlyRankingDto toDto(MonthlyStatsEntity entity);
}
