package com.akshay.iplcrickbuzz.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.akshay.iplcrickbuzz.dto.DashboardResponseDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public DashboardServiceImpl(
            PlayerRepository playerRepository,
            TeamRepository teamRepository) {

        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public DashboardResponseDTO getDashboardData() {

        long totalTeams =
                teamRepository.count();

        long totalPlayers =
                playerRepository.count();

        long totalRuns =
                playerRepository.getTotalRuns();

        long totalWickets =
                playerRepository.getTotalWickets();

        List<Player> topRunScorers =
                playerRepository.findTopRunScorer();

        List<Player> topWicketTakers =
                playerRepository.findTopWicketTaker();

        PlayerResponseDTO topRunScorer =
                topRunScorers.isEmpty()
                        ? null
                        : convertToResponseDTO(
                                topRunScorers.get(0));

        PlayerResponseDTO topWicketTaker =
                topWicketTakers.isEmpty()
                        ? null
                        : convertToResponseDTO(
                                topWicketTakers.get(0));

        return new DashboardResponseDTO(
                totalTeams,
                totalPlayers,
                totalRuns,
                totalWickets,
                topRunScorer,
                topWicketTaker
        );
    }

    private PlayerResponseDTO convertToResponseDTO(
            Player player) {

        PlayerResponseDTO dto =
                new PlayerResponseDTO();

        dto.setPlayerId(player.getPlayerId());
        dto.setJerseyNumber(player.getJerseyNumber());
        dto.setPlayerName(player.getPlayerName());
        dto.setRuns(player.getRuns());
        dto.setWickets(player.getWickets());
        dto.setSpecialization(
                player.getSpecialization());

        if (player.getTeam() != null) {

            dto.setTeamName(
                    player.getTeam().getTeamName());
        }

        return dto;
    }
}