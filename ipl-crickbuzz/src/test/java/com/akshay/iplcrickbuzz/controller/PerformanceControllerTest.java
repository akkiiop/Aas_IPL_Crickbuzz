package com.akshay.iplcrickbuzz.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.akshay.iplcrickbuzz.config.JwtUtil;
import com.akshay.iplcrickbuzz.dto.PerformanceRequestDTO;
import com.akshay.iplcrickbuzz.dto.PerformanceResponseDTO;
import com.akshay.iplcrickbuzz.service.PerformanceService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PerformanceController.class)
@AutoConfigureMockMvc(addFilters = false)
class PerformanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private PerformanceService performanceService;

    @MockitoBean
    private JwtUtil jwtUtil;

    // TEST 1: Add Performance
    @Test
    void addPerformance_Success() throws Exception {
        PerformanceRequestDTO request = new PerformanceRequestDTO();
        request.setMatchId(101);
        request.setPlayerId(1);
        request.setRunsScored(82);

        PerformanceResponseDTO response = new PerformanceResponseDTO();
        response.setPerformanceId(500);
        response.setMatchId(101);
        response.setPlayerName("Virat Kohli");
        response.setRunsScored(82);

        when(performanceService.addPerformance(any(PerformanceRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/performances")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.performanceId").value(500))
                .andExpect(jsonPath("$.playerName").value("Virat Kohli"))
                .andExpect(jsonPath("$.runsScored").value(82));
    }

    // TEST 2: Get Performances by Match
    @Test
    void getByMatch_Success() throws Exception {
        PerformanceResponseDTO response = new PerformanceResponseDTO();
        response.setPerformanceId(500);
        response.setMatchId(101);

        when(performanceService.getPerformancesByMatch(101)).thenReturn(List.of(response));

        mockMvc.perform(get("/api/performances/match/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].matchId").value(101));
    }

    // TEST 3: Delete Performance
    @Test
    void deletePerformance_Success() throws Exception {
        doNothing().when(performanceService).deletePerformance(500);

        mockMvc.perform(delete("/api/performances/500"))
                .andExpect(status().isNoContent());
    }
}
