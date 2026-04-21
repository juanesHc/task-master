package com.taskmaster.controller.ranking;

import com.taskmaster.dto.ranking.RankingComparisonDto;
import com.taskmaster.security.CurrentUserService;
import com.taskmaster.service.ranking.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;
    private final CurrentUserService currentUserService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<RankingComparisonDto> myRanking() {
        return ResponseEntity.ok(rankingService.comparisonFor(currentUserService.requireId()));
    }
}
