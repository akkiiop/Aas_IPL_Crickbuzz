package com.akshay.iplcrickbuzz.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.akshay.iplcrickbuzz.config.JwtUtil;
import com.akshay.iplcrickbuzz.dto.PlayerRequestDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;
import com.akshay.iplcrickbuzz.exception.PlayerNotFoundException;
import com.akshay.iplcrickbuzz.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PlayerController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypasses security filters for pure API unit testing
class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private PlayerService playerService;

    @MockitoBean
    private JwtUtil jwtUtil;

    private PlayerResponseDTO createSampleResponse() {
        PlayerResponseDTO dto = new PlayerResponseDTO();
        dto.setPlayerId(1);
        dto.setJerseyNumber(18);
        dto.setPlayerName("Virat Kohli");
        dto.setRuns(8000);
        dto.setWickets(5);
        dto.setSpecialization("Batsman");
        dto.setTeamName("RCB");
        return dto;
    }

    @Test
    void getPlayerById_Success() throws Exception {
        PlayerResponseDTO response = createSampleResponse();
        when(playerService.getPlayerById(1)).thenReturn(response);

        mockMvc.perform(get("/api/players/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerName").value("Virat Kohli"))
                .andExpect(jsonPath("$.teamName").value("RCB"))
                .andExpect(jsonPath("$.jerseyNumber").value(18));
    }

    @Test
    void getPlayerById_NotFound() throws Exception {
        when(playerService.getPlayerById(999))
                .thenThrow(new PlayerNotFoundException("Player not found with id: 999"));

        mockMvc.perform(get("/api/players/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void savePlayer_Success() throws Exception {
        PlayerRequestDTO request = new PlayerRequestDTO();
        request.setJerseyNumber(18);
        request.setPlayerName("Virat Kohli");
        request.setRuns(8000);
        request.setWickets(5);
        request.setTeamId(1);
        request.setSpecialization("Batsman");

        PlayerResponseDTO response = createSampleResponse();
        when(playerService.savePlayer(any(PlayerRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/players")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerName").value("Virat Kohli"))
                .andExpect(jsonPath("$.teamName").value("RCB"));
    }

    @Test
    void updatePlayer_Success() throws Exception {
        PlayerRequestDTO request = new PlayerRequestDTO();
        request.setJerseyNumber(18);
        request.setPlayerName("Virat Kohli");
        request.setRuns(8500);
        request.setWickets(5);
        request.setTeamId(1);
        request.setSpecialization("Batsman");

        PlayerResponseDTO response = createSampleResponse();
        response.setRuns(8500);

        when(playerService.updatePlayer(eq(1), any(PlayerRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/players/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.runs").value(8500));
    }

    @Test
    void deletePlayer_Success() throws Exception {
        doNothing().when(playerService).deletePlayer(1);

        mockMvc.perform(delete("/api/players/1"))
                .andExpect(status().isNoContent());
    }
}
