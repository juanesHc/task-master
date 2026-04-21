package com.taskmaster.service.task;

import com.taskmaster.exception.BusinessRuleException;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class CronEvaluator {

    public LocalDateTime nextAfter(String cron, String timezone, LocalDateTime from) {
        CronExpression expr = parse(cron);
        ZoneId zone = resolveZone(timezone);
        ZonedDateTime zdt = from.atZone(zone);
        ZonedDateTime next = expr.next(zdt);
        if (next == null) {
            throw new BusinessRuleException("Cron expression '" + cron + "' has no future occurrence after " + from);
        }
        return next.toLocalDateTime();
    }

    public void validate(String cron, String timezone) {
        parse(cron);
        resolveZone(timezone);
    }

    private CronExpression parse(String cron) {
        try {
            return CronExpression.parse(cron);
        } catch (IllegalArgumentException ex) {
            throw new BusinessRuleException("Invalid cron expression: " + ex.getMessage());
        }
    }

    private ZoneId resolveZone(String timezone) {
        if (timezone == null || timezone.isBlank()) {
            return ZoneId.of("UTC");
        }
        try {
            return ZoneId.of(timezone);
        } catch (Exception ex) {
            throw new BusinessRuleException("Invalid timezone: " + timezone);
        }
    }
}
