package com.akshay.iplcrickbuzz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akshay.iplcrickbuzz.dto.MatchRequestDTO;
import com.akshay.iplcrickbuzz.dto.MatchResponseDTO;
import com.akshay.iplcrickbuzz.dto.PerformanceResponseDTO;
import com.akshay.iplcrickbuzz.dto.ScorecardResponseDTO;
import com.akshay.iplcrickbuzz.entity.Match;
import com.akshay.iplcrickbuzz.entity.Team;
import com.akshay.iplcrickbuzz.exception.MatchNotFoundException;
import com.akshay.iplcrickbuzz.exception.TeamNotFoundException;
import com.akshay.iplcrickbuzz.repository.MatchRepository;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    
    
    @Autowired
    private PerformanceService performanceService;

    public MatchServiceImpl(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    // Helper method to convert Entity to DTO
    private MatchResponseDTO convertToResponseDTO(Match match) {
        MatchResponseDTO dto = new MatchResponseDTO();
        dto.setMatchId(match.getMatchId());
        
        dto.setTeam1Id(match.getTeam1().getTeamId());
        dto.setTeam1Name(match.getTeam1().getTeamName());
        
        dto.setTeam2Id(match.getTeam2().getTeamId());
        dto.setTeam2Name(match.getTeam2().getTeamName());
        
        dto.setVenue(match.getVenue());
        dto.setMatchDate(match.getMatchDate());
        
        if (match.getTossWinner() != null) {
            dto.setTossWinnerName(match.getTossWinner().getTeamName());
        }
        dto.setTossDecision(match.getTossDecision());
        
        if (match.getWinner() != null) {
            dto.setWinnerName(match.getWinner().getTeamName());
        }
        dto.setStatus(match.getStatus());
        
        return dto;
    }

    // Helper method to find a Team or throw an exception
    private Team getTeamOrThrow(Integer teamId) {
        if (teamId == null) return null;
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new TeamNotFoundException("Team not found with id: " + teamId));
    }

    @Override
    public MatchResponseDTO saveMatch(MatchRequestDTO request) {
        Match match = new Match();
        
        match.setTeam1(getTeamOrThrow(request.getTeam1Id()));
        match.setTeam2(getTeamOrThrow(request.getTeam2Id()));
        match.setVenue(request.getVenue());
        match.setMatchDate(request.getMatchDate());
        
        match.setTossWinner(getTeamOrThrow(request.getTossWinnerId()));
        match.setTossDecision(request.getTossDecision());
        
        match.setWinner(getTeamOrThrow(request.getWinnerId()));
        match.setStatus(request.getStatus());

        Match savedMatch = matchRepository.save(match);
        return convertToResponseDTO(savedMatch);
    }

    @Override
    public Page<MatchResponseDTO> getAllMatches(Pageable pageable) {
        return matchRepository.findAll(pageable).map(this::convertToResponseDTO);
    }

    @Override
    public MatchResponseDTO getMatchById(Integer id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("Match not found with id: " + id));
        return convertToResponseDTO(match);
    }

    @Override
    public MatchResponseDTO updateMatch(Integer id, MatchRequestDTO request) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("Match not found with id: " + id));

        match.setTeam1(getTeamOrThrow(request.getTeam1Id()));
        match.setTeam2(getTeamOrThrow(request.getTeam2Id()));
        match.setVenue(request.getVenue());
        match.setMatchDate(request.getMatchDate());
        
        match.setTossWinner(getTeamOrThrow(request.getTossWinnerId()));
        match.setTossDecision(request.getTossDecision());
        
        match.setWinner(getTeamOrThrow(request.getWinnerId()));
        match.setStatus(request.getStatus());

        Match updatedMatch = matchRepository.save(match);
        return convertToResponseDTO(updatedMatch);
    }

    @Override
    public void deleteMatch(Integer id) {
        if (!matchRepository.existsById(id)) {
            throw new MatchNotFoundException("Match not found with id: " + id);
        }
        matchRepository.deleteById(id);
    }

    @Override
    public List<MatchResponseDTO> getMatchesByStatus(String status) {
        return matchRepository.findByStatus(status).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    
    @Override
    public ScorecardResponseDTO getScorecard(Integer matchId) {
        MatchResponseDTO matchDto = this.getMatchById(matchId);

        List<PerformanceResponseDTO> allPerformances = performanceService.getPerformancesByMatch(matchId);

        List<PerformanceResponseDTO> team1Stats = allPerformances.stream()
                .filter(p -> p.getTeamName().equals(matchDto.getTeam1Name())) 
                .collect(Collectors.toList());

        List<PerformanceResponseDTO> team2Stats = allPerformances.stream()
                .filter(p -> p.getTeamName().equals(matchDto.getTeam2Name())) 
                .collect(Collectors.toList());

        ScorecardResponseDTO scorecard = new ScorecardResponseDTO();
        scorecard.setMatch(matchDto);
        scorecard.setTeam1Performances(team1Stats);
        scorecard.setTeam2Performances(team2Stats);

        return scorecard;
    }

}
