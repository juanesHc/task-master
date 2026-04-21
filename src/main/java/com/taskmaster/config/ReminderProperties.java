package com.taskmaster.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.reminder")
public class ReminderProperties {

    private Duration leadOneHour = Duration.ofHours(1);
    private Duration leadTenMinutes = Duration.ofMinutes(10);
    private String overdueSweepCron = "0 */5 * * * *";
}
