package com.taskmaster.service.ranking;

import com.taskmaster.dto.ranking.MonthlyRankingDto;
import com.taskmaster.dto.ranking.RankingComparisonDto;
import com.taskmaster.entity.MonthlyStatsEntity;
import com.taskmaster.entity.PersonEntity;
import com.taskmaster.entity.enums.ExecutionOutcomeEnum;
import com.taskmaster.exception.ResourceNotFoundException;
import com.taskmaster.mapper.ranking.RankingMapper;
import com.taskmaster.repository.MonthlyStatsRepository;
import com.taskmaster.repository.PersonRepository;
import com.taskmaster.repository.TaskExecutionLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingService {

    private final PersonRepository personRepository;
    private final MonthlyStatsRepository monthlyStatsRepository;
    private final TaskExecutionLogRepository executionLogRepository;
    private final RankingMapper rankingMapper;

    @Transactional
    public MonthlyRankingDto snapshotFor(UUID personId, YearMonth period) {
        PersonEntity person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + personId));

        LocalDateTime from = period.atDay(1).atStartOfDay();
        LocalDateTime to = period.plusMonths(1).atDay(1).atStartOfDay();

        long total = executionLogRepository.countByPersonAndWindow(personId, from, to);
        long completed = executionLogRepository.countByPersonOutcomeAndWindow(personId, ExecutionOutcomeEnum.COMPLETED, from, to);
        long missed = executionLogRepository.countByPersonOutcomeAndWindow(personId, ExecutionOutcomeEnum.MISSED, from, to);

        double rate = total == 0 ? 0d : ((double) completed / (double) total) * 100.0d;

        MonthlyStatsEntity stats = monthlyStatsRepository
                .findByPersonEntity_IdAndYearMonth(personId, period.toString())
                .orElseGet(MonthlyStatsEntity::new);
        stats.setPersonEntity(person);
        stats.setYearMonth(period.toString());
        stats.setTotalTasks((int) total);
        stats.setCompletedTasks((int) completed);
        stats.setMissedTasks((int) missed);
        stats.setCompletionRate(rate);
        monthlyStatsRepository.save(stats);

        return rankingMapper.toDto(stats);
    }

    @Transactional(readOnly = true)
    public RankingComparisonDto comparisonFor(UUID personId) {
        personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found: " + personId));

        YearMonth current = YearMonth.now();
        MonthlyRankingDto currentDto = computeInline(personId, current);

        List<MonthlyRankingDto> history = monthlyStatsRepository
                .findByPersonEntity_IdOrderByYearMonthDesc(personId).stream()
                .map(rankingMapper::toDto)
                .toList();

        MonthlyRankingDto best = history.stream()
                .max(Comparator.comparingDouble(MonthlyRankingDto::getCompletionRate))
                .orElse(currentDto);

        double avg = history.isEmpty() ? 0d :
                history.stream().mapToDouble(MonthlyRankingDto::getCompletionRate).average().orElse(0d);

        double delta = currentDto.getCompletionRate() - best.getCompletionRate();

        return new RankingComparisonDto(currentDto, best, delta, avg, history);
    }

    @Scheduled(cron = "0 0 1 * * *")
    @Transactional
    public void nightlySnapshot() {
        YearMonth current = YearMonth.now();
        LocalDate yesterday = LocalDate.now().minusDays(1);
        YearMonth target = YearMonth.from(yesterday);
        personRepository.findAll().forEach(p -> {
            snapshotFor(p.getId(), current);
            if (!target.equals(current)) {
                snapshotFor(p.getId(), target);
            }
        });
        log.info("Nightly ranking snapshot completed for {}", current);
    }

    private MonthlyRankingDto computeInline(UUID personId, YearMonth period) {
        LocalDateTime from = period.atDay(1).atStartOfDay();
        LocalDateTime to = period.plusMonths(1).atDay(1).atStartOfDay();
        long total = executionLogRepository.countByPersonAndWindow(personId, from, to);
        long completed = executionLogRepository.countByPersonOutcomeAndWindow(personId, ExecutionOutcomeEnum.COMPLETED, from, to);
        long missed = executionLogRepository.countByPersonOutcomeAndWindow(personId, ExecutionOutcomeEnum.MISSED, from, to);
        double rate = total == 0 ? 0d : ((double) completed / (double) total) * 100.0d;
        return new MonthlyRankingDto(period.toString(), (int) total, (int) completed, (int) missed, rate);
    }
}
