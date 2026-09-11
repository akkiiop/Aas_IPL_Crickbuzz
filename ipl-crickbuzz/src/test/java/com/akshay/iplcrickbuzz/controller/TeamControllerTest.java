package com.akshay.iplcrickbuzz.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.akshay.iplcrickbuzz.config.JwtUtil;
import com.akshay.iplcrickbuzz.dto.TeamRequestDTO;
import com.akshay.iplcrickbuzz.dto.TeamResponseDTO;
import com.akshay.iplcrickbuzz.exception.TeamNotFoundException;
import com.akshay.iplcrickbuzz.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(TeamController.class)
@AutoConfigureMockMvc(addFilters = false)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private TeamService teamService;

    @MockitoBean
    private JwtUtil jwtUtil;

    private TeamResponseDTO createSampleResponse() {
        TeamResponseDTO dto = new TeamResponseDTO();
        dto.setTeamId(1);
        dto.setTeamName("Royal Challengers Bengaluru");
        dto.setShortName("RCB");
        dto.setCity("Bengaluru");
        dto.setCaptain("Faf du Plessis");
        dto.setHomeGround("M. Chinnaswamy Stadium");
        return dto;
    }

    private TeamRequestDTO createSampleRequest() {
        TeamRequestDTO dto = new TeamRequestDTO();
        dto.setTeamName("Royal Challengers Bengaluru");
        dto.setShortName("RCB");
        dto.setCity("Bengaluru");
        dto.setCaptain("Faf du Plessis");
        dto.setHomeGround("M. Chinnaswamy Stadium");
        return dto;
    }

    // TEST 1: GET /api/teams -> 200 OK
    @Test
    void getAllTeams_Success() throws Exception {
        TeamResponseDTO response = createSampleResponse();
        when(teamService.getAllTeams()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shortName").value("RCB"))
                .andExpect(jsonPath("$[0].teamName").value("Royal Challengers Bengaluru"));
    }

    // TEST 2: GET /api/teams/1 -> 200 OK
    @Test
    void getTeamById_Success() throws Exception {
        TeamResponseDTO response = createSampleResponse();
        when(teamService.getTeamById(1)).thenReturn(response);

        mockMvc.perform(get("/api/teams/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortName").value("RCB"))
                .andExpect(jsonPath("$.city").value("Bengaluru"));
    }

    // TEST 3: GET /api/teams/99 -> 404 Not Found
    @Test
    void getTeamById_NotFound() throws Exception {
        when(teamService.getTeamById(99))
                .thenThrow(new TeamNotFoundException("Team not found with id: 99"));

        mockMvc.perform(get("/api/teams/99"))
                .andExpect(status().isNotFound());
    }

    // TEST 4: POST /api/teams -> 201 Created
    @Test
    void saveTeam_Success() throws Exception {
        TeamRequestDTO request = createSampleRequest();
        TeamResponseDTO response = createSampleResponse();

        when(teamService.saveTeam(any(TeamRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.shortName").value("RCB"));
    }

    // TEST 5: PUT /api/teams/1 -> 200 OK
    @Test
    void updateTeam_Success() throws Exception {
        TeamRequestDTO request = createSampleRequest();
        TeamResponseDTO response = createSampleResponse();

        when(teamService.updateTeam(eq(1), any(TeamRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/teams/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.teamName").value("Royal Challengers Bengaluru"));
    }

    // TEST 6: DELETE /api/teams/1 -> 204 No Content
    @Test
    void deleteTeam_Success() throws Exception {
        doNothing().when(teamService).deleteTeam(1);

        mockMvc.perform(delete("/api/teams/1"))
                .andExpect(status().isNoContent());
    }
}
