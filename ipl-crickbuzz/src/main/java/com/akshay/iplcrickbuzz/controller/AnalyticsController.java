package com.akshay.iplcrickbuzz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.iplcrickbuzz.dto.PlayerComparisonDTO;
import com.akshay.iplcrickbuzz.dto.PlayerStatsDTO;
import com.akshay.iplcrickbuzz.dto.TeamStatsDTO;
import com.akshay.iplcrickbuzz.service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/player/{id}")
    public ResponseEntity<PlayerStatsDTO> getPlayerStats(@PathVariable Integer id) {
        return ResponseEntity.ok(analyticsService.getPlayerStats(id));
    }

    @GetMapping("/team/{id}")
    public ResponseEntity<TeamStatsDTO> getTeamStats(@PathVariable Integer id) {
        return ResponseEntity.ok(analyticsService.getTeamStats(id));
    }
    
    @GetMapping("/compare")
    public ResponseEntity<PlayerComparisonDTO> comparePlayers(
            @RequestParam Integer player1, 
            @RequestParam Integer player2) {
            
        return ResponseEntity.ok(analyticsService.comparePlayers(player1, player2));
    }

}
