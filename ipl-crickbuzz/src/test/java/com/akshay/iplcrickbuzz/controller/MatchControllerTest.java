package com.akshay.iplcrickbuzz.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.akshay.iplcrickbuzz.config.JwtUtil;
import com.akshay.iplcrickbuzz.dto.MatchRequestDTO;
import com.akshay.iplcrickbuzz.dto.MatchResponseDTO;
import com.akshay.iplcrickbuzz.service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@WebMvcTest(MatchController.class)
@AutoConfigureMockMvc(addFilters = false)
class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @MockitoBean
    private MatchService matchService;

    @MockitoBean
    private JwtUtil jwtUtil;

    // TEST 1: Create Match
    @Test
    void createMatch_Success() throws Exception {
        MatchRequestDTO request = new MatchRequestDTO();
        request.setTeam1Id(1);
        request.setTeam2Id(2);
        request.setVenue("Wankhede Stadium");
        request.setMatchDate(LocalDate.of(2026, 4, 10));

        MatchResponseDTO response = new MatchResponseDTO();
        response.setMatchId(101); // <--- setMatchId instead of setId
        response.setTeam1Name("Mumbai Indians");
        response.setTeam2Name("Chennai Super Kings");
        response.setVenue("Wankhede Stadium");
        response.setStatus("UPCOMING");

        when(matchService.saveMatch(any(MatchRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/matches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matchId").value(101)) // <--- $.matchId instead of $.id
                .andExpect(jsonPath("$.team1Name").value("Mumbai Indians"))
                .andExpect(jsonPath("$.venue").value("Wankhede Stadium"));
    }

    // TEST 2: Get Match by ID
    @Test
    void getMatchById_Success() throws Exception {
        MatchResponseDTO response = new MatchResponseDTO();
        response.setMatchId(101);
        response.setTeam1Name("Mumbai Indians");
        response.setTeam2Name("Chennai Super Kings");

        when(matchService.getMatchById(101)).thenReturn(response);

        mockMvc.perform(get("/api/matches/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.matchId").value(101))
                .andExpect(jsonPath("$.team1Name").value("Mumbai Indians"));
    }

    // TEST 3: Get Matches by Status
    @Test
    void getMatchesByStatus_Success() throws Exception {
        MatchResponseDTO m1 = new MatchResponseDTO();
        m1.setMatchId(101);
        m1.setStatus("UPCOMING");

        when(matchService.getMatchesByStatus("UPCOMING")).thenReturn(List.of(m1));

        mockMvc.perform(get("/api/matches/status/UPCOMING"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].status").value("UPCOMING"));
    }

    // TEST 4: Delete Match
    @Test
    void deleteMatch_Success() throws Exception {
        doNothing().when(matchService).deleteMatch(101);

        mockMvc.perform(delete("/api/matches/101"))
                .andExpect(status().isNoContent());
    }
}
