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

import com.akshay.iplcrickbuzz.dto.PerformanceRequestDTO;
import com.akshay.iplcrickbuzz.dto.PerformanceResponseDTO;
import com.akshay.iplcrickbuzz.entity.Match;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.entity.PlayerPerformance;
import com.akshay.iplcrickbuzz.entity.Team;
import com.akshay.iplcrickbuzz.repository.MatchRepository;
import com.akshay.iplcrickbuzz.repository.PerformanceRepository;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;

@ExtendWith(MockitoExtension.class)
class PerformanceServiceImplTest {

    @Mock
    private PerformanceRepository performanceRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private PerformanceServiceImpl performanceService;

    // Helper method to setup fake data
    private PlayerPerformance createSamplePerformance() {
        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("RCB");

        Player player = new Player();
        player.setPlayerId(1);
        player.setPlayerName("Virat Kohli");
        player.setTeam(team);

        Match match = new Match();
        match.setMatchId(101);

        PlayerPerformance performance = new PlayerPerformance();
        performance.setPerformanceId(1001);
        performance.setMatch(match);
        performance.setPlayer(player);
        performance.setRunsScored(82);
        performance.setBallsFaced(53);
        performance.setWicketsTaken(0);
        performance.setOversBowled(0.0);
        performance.setRunsConceded(0);
        performance.setCatches(2);

        return performance;
    }

    // TEST 1: Add Performance Success
    @Test
    void addPerformance_Success() {
        PlayerPerformance performance = createSamplePerformance();
        PerformanceRequestDTO request = new PerformanceRequestDTO();
        request.setMatchId(101);
        request.setPlayerId(1);
        request.setRunsScored(82);
        request.setBallsFaced(53);

        when(matchRepository.findById(101)).thenReturn(Optional.of(performance.getMatch()));
        when(playerRepository.findById(1)).thenReturn(Optional.of(performance.getPlayer()));
        when(performanceRepository.save(any(PlayerPerformance.class))).thenReturn(performance);

        PerformanceResponseDTO response = performanceService.addPerformance(request);

        assertEquals("Virat Kohli", response.getPlayerName());
        assertEquals("RCB", response.getTeamName());
        assertEquals(82, response.getRunsScored());
        verify(performanceRepository).save(any(PlayerPerformance.class));
    }

    // TEST 2: Add Performance - Match Not Found
    @Test
    void addPerformance_MatchNotFound_ThrowsException() {
        PerformanceRequestDTO request = new PerformanceRequestDTO();
        request.setMatchId(999);
        request.setPlayerId(1);

        when(matchRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            performanceService.addPerformance(request);
        });

        verify(performanceRepository, never()).save(any(PlayerPerformance.class));
    }

    // TEST 3: Get Performances By Match
    @Test
    void getPerformancesByMatch_Success() {
        PlayerPerformance performance = createSamplePerformance();
        when(performanceRepository.findByMatch_MatchId(101)).thenReturn(List.of(performance));

        List<PerformanceResponseDTO> results = performanceService.getPerformancesByMatch(101);

        assertEquals(1, results.size());
        assertEquals("Virat Kohli", results.get(0).getPlayerName());
    }

    // TEST 4: Update Performance
    @Test
    void updatePerformance_Success() {
        PlayerPerformance performance = createSamplePerformance();
        PerformanceRequestDTO request = new PerformanceRequestDTO();
        request.setRunsScored(100);

        when(performanceRepository.findById(1001)).thenReturn(Optional.of(performance));
        when(performanceRepository.save(any(PlayerPerformance.class))).thenReturn(performance);

        PerformanceResponseDTO response = performanceService.updatePerformance(1001, request);

        assertNotNull(response);
        verify(performanceRepository).save(any(PlayerPerformance.class));
    }

    // TEST 5: Delete Performance
    @Test
    void deletePerformance_Success() {
        PlayerPerformance performance = createSamplePerformance();
        when(performanceRepository.findById(1001)).thenReturn(Optional.of(performance));

        performanceService.deletePerformance(1001);

        verify(performanceRepository).delete(performance);
    }
}
