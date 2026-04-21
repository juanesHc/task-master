package com.taskmaster.repository;

import com.taskmaster.entity.MonthlyStatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MonthlyStatsRepository extends JpaRepository<MonthlyStatsEntity, UUID> {

    Optional<MonthlyStatsEntity> findByPersonEntity_IdAndYearMonth(UUID personId, String yearMonth);

    List<MonthlyStatsEntity> findByPersonEntity_IdOrderByYearMonthDesc(UUID personId);
}
