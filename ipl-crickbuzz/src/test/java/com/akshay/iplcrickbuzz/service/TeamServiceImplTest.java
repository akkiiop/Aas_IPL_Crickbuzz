package com.akshay.iplcrickbuzz.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.akshay.iplcrickbuzz.dto.TeamRequestDTO;
import com.akshay.iplcrickbuzz.dto.TeamResponseDTO;
import com.akshay.iplcrickbuzz.entity.Team;
import com.akshay.iplcrickbuzz.exception.TeamNotFoundException;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamServiceImpl teamService;

    // Helper method to create a sample Team
    private Team createSampleTeam() {
        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("Royal Challengers Bengaluru");
        team.setShortName("RCB");
        team.setCity("Bengaluru");
        team.setCaptain("Faf du Plessis");
        team.setHomeGround("M. Chinnaswamy Stadium");
        return team;
    }

    // Helper method to create a sample TeamRequestDTO
    private TeamRequestDTO createSampleRequest() {
        TeamRequestDTO dto = new TeamRequestDTO();
        dto.setTeamName("Royal Challengers Bengaluru");
        dto.setShortName("RCB");
        dto.setCity("Bengaluru");
        dto.setCaptain("Faf du Plessis");
        dto.setHomeGround("M. Chinnaswamy Stadium");
        return dto;
    }

    // TEST 1: Save Team
    @Test
    void saveTeam_Success() {
        TeamRequestDTO request = createSampleRequest();
        Team team = createSampleTeam();

        when(teamRepository.save(any(Team.class))).thenReturn(team);

        TeamResponseDTO result = teamService.saveTeam(request);

        assertEquals("Royal Challengers Bengaluru", result.getTeamName());
        assertEquals("RCB", result.getShortName());
        verify(teamRepository).save(any(Team.class));
    }

    // TEST 2: Get All Teams
    @Test
    void getAllTeams_ReturnsList() {
        Team team = createSampleTeam();
        when(teamRepository.findAll()).thenReturn(List.of(team));

        List<TeamResponseDTO> results = teamService.getAllTeams();

        assertEquals(1, results.size());
        assertEquals("RCB", results.get(0).getShortName());
        verify(teamRepository).findAll();
    }

    // TEST 3: Get Team By ID - Success
    @Test
    void getTeamById_Success() {
        Team team = createSampleTeam();
        when(teamRepository.findById(1)).thenReturn(Optional.of(team));

        TeamResponseDTO result = teamService.getTeamById(1);

        assertEquals("Royal Challengers Bengaluru", result.getTeamName());
        verify(teamRepository).findById(1);
    }

    // TEST 4: Get Team By ID - Not Found
    @Test
    void getTeamById_NotFound_ThrowsException() {
        when(teamRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, () -> {
            teamService.getTeamById(99);
        });
    }

    // TEST 5: Update Team - Success
    @Test
    void updateTeam_Success() {
        Team team = createSampleTeam();
        TeamRequestDTO request = createSampleRequest();

        when(teamRepository.findById(1)).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(team);

        TeamResponseDTO result = teamService.updateTeam(1, request);

        assertEquals("Royal Challengers Bengaluru", result.getTeamName());
        verify(teamRepository).findById(1);
        verify(teamRepository).save(any(Team.class));
    }

    // TEST 6: Update Team - Not Found
    @Test
    void updateTeam_NotFound_ThrowsException() {
        TeamRequestDTO request = createSampleRequest();
        when(teamRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(TeamNotFoundException.class, () -> {
            teamService.updateTeam(99, request);
        });

        verify(teamRepository, never()).save(any(Team.class));
    }

    // TEST 7: Delete Team - Success
    @Test
    void deleteTeam_Success() {
        when(teamRepository.existsById(1)).thenReturn(true);

        teamService.deleteTeam(1);

        verify(teamRepository).existsById(1);
        verify(teamRepository).deleteById(1);
    }

    // TEST 8: Delete Team - Not Found
    @Test
    void deleteTeam_NotFound_ThrowsException() {
        when(teamRepository.existsById(99)).thenReturn(false);

        assertThrows(TeamNotFoundException.class, () -> {
            teamService.deleteTeam(99);
        });

        verify(teamRepository, never()).deleteById(anyInt());
    }
}
