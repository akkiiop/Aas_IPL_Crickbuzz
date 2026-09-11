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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.akshay.iplcrickbuzz.dto.PlayerRequestDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.entity.Team;
import com.akshay.iplcrickbuzz.exception.PlayerNotFoundException;
import com.akshay.iplcrickbuzz.exception.TeamNotFoundException;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private PlayerServiceImpl playerService;
    
    
    private Player createSamplePlayer() {
        // First create a Team (because Player belongs to a Team)
        Team team = new Team();
        team.setTeamId(1);
        team.setTeamName("RCB");
        // Now create the Player
        Player player = new Player();
        player.setPlayerId(1);
        player.setJerseyNumber(18);
        player.setPlayerName("Virat Kohli");
        player.setRuns(8000);
        player.setWickets(5);
        player.setSpecialization("Batsman");
        player.setTeam(team);  // Link player to team
        return player;
    }
    
    private PlayerRequestDTO createSampleRequest() {
        PlayerRequestDTO dto = new PlayerRequestDTO();
        dto.setJerseyNumber(18);
        dto.setPlayerName("Virat Kohli");
        dto.setRuns(8000);
        dto.setWickets(5);
        dto.setTeamId(1);           // just the team ID (not full team object)
        dto.setSpecialization("Batsman");
        return dto;
    }
    
    // =====================================================
    // TEST 1: savePlayer — Happy Path (everything works)
    // =====================================================
    @Test
    void savePlayer_Success() {
        // ARRANGE: Set up fake scenario
        PlayerRequestDTO request = createSampleRequest();
        Player player = createSamplePlayer();
        Team team = player.getTeam();

        when(teamRepository.findById(1)).thenReturn(Optional.of(team));
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        // ACT: Call the real service method
        PlayerResponseDTO result = playerService.savePlayer(request);

        // ASSERT: Check the returned results
        assertEquals("Virat Kohli", result.getPlayerName());
        assertEquals("RCB", result.getTeamName());
        assertEquals(8000, result.getRuns());

        // VERIFY: Ensure repositories were actually called
        verify(teamRepository).findById(1);
        verify(playerRepository).save(any(Player.class));
    }

    // =====================================================
    // TEST 2: savePlayer — Team Not Found (should throw error)
    // =====================================================
    @Test
    void savePlayer_TeamNotFound_ThrowsException() {
        // ARRANGE: Team ID 1 does not exist in DB
        PlayerRequestDTO request = createSampleRequest();
        when(teamRepository.findById(1)).thenReturn(Optional.empty());

        // ACT + ASSERT: Expect TeamNotFoundException
        assertThrows(TeamNotFoundException.class, () -> {
            playerService.savePlayer(request);
        });

        // VERIFY: save() should NEVER be called if team wasn't found
        verify(playerRepository, never()).save(any(Player.class));
    }

    // =====================================================
    // TEST 3: getPlayerById — Found
    // =====================================================
    @Test
    void getPlayerById_Found() {
        // ARRANGE
        Player player = createSamplePlayer();
        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        // ACT
        PlayerResponseDTO result = playerService.getPlayerById(1);

        // ASSERT
        assertEquals("Virat Kohli", result.getPlayerName());
        assertEquals(18, result.getJerseyNumber());
        assertEquals("RCB", result.getTeamName());

        verify(playerRepository).findById(1);
    }

    // =====================================================
    // TEST 4: getPlayerById — NOT Found
    // =====================================================
    @Test
    void getPlayerById_NotFound_ThrowsException() {
        // ARRANGE
        when(playerRepository.findById(999)).thenReturn(Optional.empty());

        // ACT + ASSERT
        PlayerNotFoundException exception = assertThrows(
                PlayerNotFoundException.class,
                () -> playerService.getPlayerById(999)
        );

        assertTrue(exception.getMessage().contains("999"));
    }

    // =====================================================
    // TEST 5: getAllPlayers — Returns Paginated Results
    // =====================================================
    @Test
    void getAllPlayers_ReturnsPage() {
        // ARRANGE
        Player player = createSamplePlayer();
        Pageable pageable = PageRequest.of(0, 10);
        Page<Player> playerPage = new PageImpl<>(List.of(player));

        when(playerRepository.findAll(pageable)).thenReturn(playerPage);

        // ACT
        Page<PlayerResponseDTO> result = playerService.getAllPlayers(pageable);

        // ASSERT
        assertEquals(1, result.getTotalElements());
        assertEquals("Virat Kohli", result.getContent().get(0).getPlayerName());
    }

    // =====================================================
    // TEST 6: deletePlayer — Success
    // =====================================================
    @Test
    void deletePlayer_Success() {
        // ARRANGE
        Player player = createSamplePlayer();
        when(playerRepository.findById(1)).thenReturn(Optional.of(player));

        // ACT
        playerService.deletePlayer(1);

        // VERIFY
        verify(playerRepository).delete(player);
    }

    // =====================================================
    // TEST 7: searchPlayers — Returns Results
    // =====================================================
    @Test
    void searchPlayers_ReturnsResults() {
        // ARRANGE
        Player player = createSamplePlayer();
        when(playerRepository.findByPlayerNameContainingIgnoreCase("virat"))
                .thenReturn(List.of(player));

        // ACT
        List<PlayerResponseDTO> results = playerService.searchPlayers("virat");

        // ASSERT
        assertEquals(1, results.size());
        assertEquals("Virat Kohli", results.get(0).getPlayerName());
    }


}
