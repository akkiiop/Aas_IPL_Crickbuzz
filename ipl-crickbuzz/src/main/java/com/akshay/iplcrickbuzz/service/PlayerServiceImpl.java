package com.akshay.iplcrickbuzz.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akshay.iplcrickbuzz.dto.PlayerRequestDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.entity.Team;
import com.akshay.iplcrickbuzz.exception.PlayerNotFoundException;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    // Constructor Injection
    public PlayerServiceImpl(
            PlayerRepository playerRepository,
            TeamRepository teamRepository) {

        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }


    // =====================================================
    // ENTITY → RESPONSE DTO
    // =====================================================

    private PlayerResponseDTO convertToResponseDTO(Player player) {

        PlayerResponseDTO dto = new PlayerResponseDTO();

        dto.setPlayerId(player.getPlayerId());
        dto.setJerseyNumber(player.getJerseyNumber());
        dto.setPlayerName(player.getPlayerName());
        dto.setRuns(player.getRuns());
        dto.setWickets(player.getWickets());

        // Get team name through Team relationship
        if (player.getTeam() != null) {
            dto.setTeamName(player.getTeam().getTeamName());
        }

        dto.setSpecialization(player.getSpecialization());

        return dto;
    }


    // =====================================================
    // CREATE PLAYER
    // =====================================================

    @Override
    public PlayerResponseDTO savePlayer(PlayerRequestDTO dto) {

        Player player = new Player();

        player.setJerseyNumber(dto.getJerseyNumber());
        player.setPlayerName(dto.getPlayerName());
        player.setRuns(dto.getRuns());
        player.setWickets(dto.getWickets());
        player.setSpecialization(dto.getSpecialization());


        // Find Team using teamId
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Team not found with id: "
                                        + dto.getTeamId()
                        )
                );


        // Connect Player with Team
        player.setTeam(team);


        Player savedPlayer = playerRepository.save(player);

        return convertToResponseDTO(savedPlayer);
    }


    // =====================================================
    // GET ALL PLAYERS
    // =====================================================

    @Override
    public Page<PlayerResponseDTO> getAllPlayers(Pageable pageable) {

        Page<Player> players =
                playerRepository.findAll(pageable);

        return players.map(this::convertToResponseDTO);
    }


    // =====================================================
    // GET PLAYER BY ID
    // =====================================================

    @Override
    public PlayerResponseDTO getPlayerById(Integer id) {

        Player player = playerRepository.findById(id)
                .orElseThrow(() ->
                        new PlayerNotFoundException(
                                "Player not found with id: " + id
                        )
                );

        return convertToResponseDTO(player);
    }


    // =====================================================
    // UPDATE PLAYER
    // =====================================================

    @Override
    public PlayerResponseDTO updatePlayer(
            Integer id,
            PlayerRequestDTO dto) {

        Player player = playerRepository.findById(id)
                .orElseThrow(() ->
                        new PlayerNotFoundException(
                                "Player not found with id: " + id
                        )
                );


        player.setJerseyNumber(dto.getJerseyNumber());
        player.setPlayerName(dto.getPlayerName());
        player.setRuns(dto.getRuns());
        player.setWickets(dto.getWickets());
        player.setSpecialization(dto.getSpecialization());


        // Find new Team
        Team team = teamRepository.findById(dto.getTeamId())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Team not found with id: "
                                        + dto.getTeamId()
                        )
                );


        // Update Player → Team relationship
        player.setTeam(team);


        Player updatedPlayer = playerRepository.save(player);

        return convertToResponseDTO(updatedPlayer);
    }


    // =====================================================
    // DELETE PLAYER
    // =====================================================

    @Override
    public void deletePlayer(Integer id) {

        Player player = playerRepository.findById(id)
                .orElseThrow(() ->
                        new PlayerNotFoundException(
                                "Player not found with id: " + id
                        )
                );

        playerRepository.delete(player);
    }
    
    
    @Override
    public List<PlayerResponseDTO> searchPlayers(String name) {

        List<Player> players =
                playerRepository
                        .findByPlayerNameContainingIgnoreCase(name);

        return players.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }
}