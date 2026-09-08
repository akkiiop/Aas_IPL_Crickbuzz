package com.akshay.iplcrickbuzz.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.iplcrickbuzz.dto.DashboardResponseDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@Service
public class DashboardServiceImpl implements DashboardService {
	
	@Autowired
	private PerformanceRepository performanceRepository;

	@Autowired
	private MatchRepository matchRepository;

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
        
        long totalMatches = matchRepository.count();

        
        List<Player> topRunScorers =
                performanceRepository.findTopRunScorerFromPerformances(PageRequest.of(0, 1));

        List<Player> topWicketTakers =
                performanceRepository.findTopWicketTakerFromPerformances(PageRequest.of(0, 1));

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

        DashboardResponseDTO response = new DashboardResponseDTO(
                totalTeams,
                totalPlayers,
                totalRuns,
                totalWickets,
                topRunScorer,
                topWicketTaker
        );
        response.setTotalMatches(totalMatches);
        
        return response;
        
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