package com.akshay.iplcrickbuzz.service;

import com.akshay.iplcrickbuzz.dto.PlayerComparisonDTO;
import com.akshay.iplcrickbuzz.dto.PlayerStatsDTO;
import com.akshay.iplcrickbuzz.dto.TeamStatsDTO;

public interface AnalyticsService {
    PlayerStatsDTO getPlayerStats(Integer playerId);
    
    TeamStatsDTO getTeamStats(Integer teamId);

	PlayerComparisonDTO comparePlayers(Integer player1Id, Integer player2Id);

}
