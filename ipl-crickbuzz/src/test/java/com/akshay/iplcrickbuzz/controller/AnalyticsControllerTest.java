package com.akshay.iplcrickbuzz.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.akshay.iplcrickbuzz.config.JwtUtil;
import com.akshay.iplcrickbuzz.dto.PlayerComparisonDTO;
import com.akshay.iplcrickbuzz.dto.PlayerStatsDTO;
import com.akshay.iplcrickbuzz.dto.TeamStatsDTO;
import com.akshay.iplcrickbuzz.service.AnalyticsService;

@WebMvcTest(AnalyticsController.class)
@AutoConfigureMockMvc(addFilters = false)
class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AnalyticsService analyticsService;

    @MockitoBean
    private JwtUtil jwtUtil;

    // TEST 1: Get Player Stats
    @Test
    void getPlayerStats_Success() throws Exception {
        PlayerStatsDTO dto = new PlayerStatsDTO();
        dto.setPlayerId(1);
        dto.setPlayerName("Virat Kohli");
        dto.setTotalRuns(7500);

        when(analyticsService.getPlayerStats(1)).thenReturn(dto);

        mockMvc.perform(get("/api/analytics/player/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerId").value(1))
                .andExpect(jsonPath("$.playerName").value("Virat Kohli"))
                .andExpect(jsonPath("$.totalRuns").value(7500));
    }

    // TEST 2: Get Team Stats
    @Test
    void getTeamStats_Success() throws Exception {
        TeamStatsDTO dto = new TeamStatsDTO();
        dto.setTeamId(10);
        dto.setTeamName("Royal Challengers Bengaluru");
        dto.setWins(50);

        when(analyticsService.getTeamStats(10)).thenReturn(dto);

        mockMvc.perform(get("/api/analytics/team/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamId").value(10))
                .andExpect(jsonPath("$.teamName").value("Royal Challengers Bengaluru"))
                .andExpect(jsonPath("$.wins").value(50));
    }

    // TEST 3: Compare Players
    @Test
    void comparePlayers_Success() throws Exception {
        PlayerStatsDTO p1 = new PlayerStatsDTO();
        p1.setPlayerId(1);
        p1.setPlayerName("Virat Kohli");

        PlayerStatsDTO p2 = new PlayerStatsDTO();
        p2.setPlayerId(2);
        p2.setPlayerName("MS Dhoni");

        PlayerComparisonDTO comp = new PlayerComparisonDTO();
        comp.setPlayer1Stats(p1);
        comp.setPlayer2Stats(p2);

        when(analyticsService.comparePlayers(1, 2)).thenReturn(comp);

        mockMvc.perform(get("/api/analytics/compare?player1=1&player2=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player1Stats.playerName").value("Virat Kohli"))
                .andExpect(jsonPath("$.player2Stats.playerName").value("MS Dhoni"));
    }
}
