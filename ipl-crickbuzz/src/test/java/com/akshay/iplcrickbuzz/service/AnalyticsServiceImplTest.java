package com.akshay.iplcrickbuzz.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.akshay.iplcrickbuzz.dto.PlayerComparisonDTO;
import com.akshay.iplcrickbuzz.dto.PlayerStatsDTO;
import com.akshay.iplcrickbuzz.dto.TeamStatsDTO;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.entity.PlayerPerformance;
import com.akshay.iplcrickbuzz.entity.Team;
import com.akshay.iplcrickbuzz.repository.MatchRepository;
import com.akshay.iplcrickbuzz.repository.PerformanceRepository;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class AnalyticsServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private MatchRepository matchRepository;

    @InjectMocks
    private AnalyticsServiceImpl analyticsService;

    // TEST 1: Get Player Stats
    @Test
    void getPlayerStats_Success() {
        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("RCB");

        Player player = new Player();
        player.setPlayerId(1);
        player.setPlayerName("Virat Kohli");
        player.setTeam(team);

        PlayerPerformance p1 = new PlayerPerformance();
        p1.setRunsScored(80);
        p1.setBallsFaced(50);
        p1.setWicketsTaken(0);
        p1.setOversBowled(0.0);
        p1.setRunsConceded(0);
        p1.setCatches(1);

        PlayerPerformance p2 = new PlayerPerformance();
        p2.setRunsScored(100);
        p2.setBallsFaced(60);
        p2.setWicketsTaken(0);
        p2.setOversBowled(0.0);
        p2.setRunsConceded(0);
        p2.setCatches(0);

        when(playerRepository.findById(1)).thenReturn(Optional.of(player));
        when(performanceRepository.findByPlayer_PlayerId(1)).thenReturn(List.of(p1, p2));

        PlayerStatsDTO stats = analyticsService.getPlayerStats(1);

        assertEquals("Virat Kohli", stats.getPlayerName());
        assertEquals("RCB", stats.getTeamName());
        assertEquals(2, stats.getMatchesPlayed());
        assertEquals(180, stats.getTotalRuns());
        assertEquals(100, stats.getHighestScore());
        assertEquals(90.0, stats.getBattingAverage());   // 180 / 2
        assertEquals(163.63636363636365, stats.getStrikeRate()); // (180 / 110) * 100
    }

    // TEST 2: Player Not Found
    @Test
    void getPlayerStats_PlayerNotFound_ThrowsException() {
        when(playerRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            analyticsService.getPlayerStats(999);
        });
    }

    // TEST 3: Get Team Stats
    @Test
    void getTeamStats_Success() {
        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("RCB");

        when(teamRepository.findById(1)).thenReturn(Optional.of(team));
        when(matchRepository.countByTeam1OrTeam2(team, team)).thenReturn(10L);
        when(matchRepository.countByWinner(team)).thenReturn(7L);

        TeamStatsDTO stats = analyticsService.getTeamStats(1);

        assertEquals("RCB", stats.getTeamName());
        assertEquals(10, stats.getMatchesPlayed());
        assertEquals(7, stats.getWins());
        assertEquals(3, stats.getLosses());
        assertEquals(70.0, stats.getWinPercentage());
    }

    // TEST 4: Compare Players
    @Test
    void comparePlayers_Success() {
        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("RCB");

        Player p1 = new Player();
        p1.setPlayerId(1);
        p1.setPlayerName("Virat Kohli");
        p1.setTeam(team);

        Player p2 = new Player();
        p2.setPlayerId(2);
        p2.setPlayerName("Faf du Plessis");
        p2.setTeam(team);

        when(playerRepository.findById(1)).thenReturn(Optional.of(p1));
        when(playerRepository.findById(2)).thenReturn(Optional.of(p2));
        when(performanceRepository.findByPlayer_PlayerId(1)).thenReturn(List.of());
        when(performanceRepository.findByPlayer_PlayerId(2)).thenReturn(List.of());

        PlayerComparisonDTO comparison = analyticsService.comparePlayers(1, 2);

        assertEquals("Virat Kohli", comparison.getPlayer1Stats().getPlayerName());
        assertEquals("Faf du Plessis", comparison.getPlayer2Stats().getPlayerName());
    }
}
