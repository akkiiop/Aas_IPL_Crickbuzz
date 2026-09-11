package com.akshay.iplcrickbuzz.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.akshay.iplcrickbuzz.dto.MatchRequestDTO;
import com.akshay.iplcrickbuzz.dto.MatchResponseDTO;
import com.akshay.iplcrickbuzz.entity.Match;
import com.akshay.iplcrickbuzz.entity.Team;
import com.akshay.iplcrickbuzz.exception.MatchNotFoundException;
import com.akshay.iplcrickbuzz.repository.MatchRepository;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class MatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private MatchServiceImpl matchService;

    // Helper method to create sample Teams
    private Team createTeam(Integer id, String name, String shortName) {
        Team team = new Team();
        team.setTeamId(id);
        team.setTeamName(name);
        team.setShortName(shortName);
        return team;
    }

    // Helper method to create a sample Match
    private Match createSampleMatch() {
        Team rcb = createTeam(1, "Royal Challengers Bengaluru", "RCB");
        Team csk = createTeam(2, "Chennai Super Kings", "CSK");

        Match match = new Match();
        match.setMatchId(101);
        match.setTeam1(rcb);
        match.setTeam2(csk);
        match.setVenue("M. Chinnaswamy Stadium");
        match.setMatchDate(LocalDate.now());
        match.setTossWinner(rcb);
        match.setTossDecision("BAT");
        match.setWinner(rcb);
        match.setStatus("COMPLETED");
        return match;
    }

    // Helper method to create a sample MatchRequestDTO
    private MatchRequestDTO createSampleRequest() {
        MatchRequestDTO dto = new MatchRequestDTO();
        dto.setTeam1Id(1);
        dto.setTeam2Id(2);
        dto.setVenue("M. Chinnaswamy Stadium");
        dto.setMatchDate(LocalDate.now());
        dto.setTossWinnerId(1);
        dto.setTossDecision("BAT");
        dto.setWinnerId(1);
        dto.setStatus("COMPLETED");
        return dto;
    }

    // TEST 1: Save Match
    @Test
    void saveMatch_Success() {
        MatchRequestDTO request = createSampleRequest();
        Match match = createSampleMatch();

        when(teamRepository.findById(1)).thenReturn(Optional.of(match.getTeam1()));
        when(teamRepository.findById(2)).thenReturn(Optional.of(match.getTeam2()));
        when(matchRepository.save(any(Match.class))).thenReturn(match);

        MatchResponseDTO result = matchService.saveMatch(request);

        assertEquals("Royal Challengers Bengaluru", result.getTeam1Name());
        assertEquals("Chennai Super Kings", result.getTeam2Name());
        assertEquals("COMPLETED", result.getStatus());

        verify(matchRepository).save(any(Match.class));
    }

    // TEST 2: Get Match By ID - Success
    @Test
    void getMatchById_Success() {
        Match match = createSampleMatch();
        when(matchRepository.findById(101)).thenReturn(Optional.of(match));

        MatchResponseDTO result = matchService.getMatchById(101);

        assertEquals("M. Chinnaswamy Stadium", result.getVenue());
        assertEquals("Royal Challengers Bengaluru", result.getWinnerName());
        verify(matchRepository).findById(101);
    }

    // TEST 3: Get Match By ID - Not Found
    @Test
    void getMatchById_NotFound_ThrowsException() {
        when(matchRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(MatchNotFoundException.class, () -> {
            matchService.getMatchById(999);
        });
    }

    // TEST 4: Get All Matches Paginated
    @Test
    void getAllMatches_ReturnsPage() {
        Match match = createSampleMatch();
        Pageable pageable = PageRequest.of(0, 5);
        Page<Match> matchPage = new PageImpl<>(List.of(match));

        when(matchRepository.findAll(pageable)).thenReturn(matchPage);

        Page<MatchResponseDTO> result = matchService.getAllMatches(pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("M. Chinnaswamy Stadium", result.getContent().get(0).getVenue());
    }

    // TEST 5: Delete Match - Success
    @Test
    void deleteMatch_Success() {
        when(matchRepository.existsById(101)).thenReturn(true);

        matchService.deleteMatch(101);

        verify(matchRepository).existsById(101);
        verify(matchRepository).deleteById(101);
    }

    // TEST 6: Delete Match - Not Found
    @Test
    void deleteMatch_NotFound_ThrowsException() {
        when(matchRepository.existsById(999)).thenReturn(false);

        assertThrows(MatchNotFoundException.class, () -> {
            matchService.deleteMatch(999);
        });

        verify(matchRepository, never()).deleteById(anyInt());
    }
}
