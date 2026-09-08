package com.akshay.iplcrickbuzz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.iplcrickbuzz.dto.PerformanceRequestDTO;
import com.akshay.iplcrickbuzz.dto.PerformanceResponseDTO;
import com.akshay.iplcrickbuzz.entity.Match;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.entity.PlayerPerformance;
import com.akshay.iplcrickbuzz.repository.MatchRepository;
import com.akshay.iplcrickbuzz.repository.PerformanceRepository;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;

@Service
public class PerformanceServiceImpl implements PerformanceService {
	
	@Autowired
	private PerformanceRepository performanceRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Autowired
	private PlayerRepository playerRepository;
	
	
	@Override
	public PerformanceResponseDTO addPerformance(PerformanceRequestDTO dto) {
	    
	    // Step 1: Fetch and Validate Match and Player
	    Match match = matchRepository.findById(dto.getMatchId())
	            .orElseThrow(() -> new RuntimeException("Match not found")); // You can use your custom exceptions here!
	            
	    Player player = playerRepository.findById(dto.getPlayerId())
	            .orElseThrow(() -> new RuntimeException("Player not found"));

	    // Step 2: Convert Request DTO to Entity (The raw database row)
	    PlayerPerformance performance = new PlayerPerformance();
	    performance.setMatch(match);
	    performance.setPlayer(player);
	    performance.setRunsScored(dto.getRunsScored());
	    // ... (You should set the rest of the stats from the dto: balls, wickets, etc.) ...

	    // Step 3: Save to Database
	    PlayerPerformance savedPerformance = performanceRepository.save(performance);

	    // Step 4: Convert saved Entity back to a nice Response DTO using our helper method!
	    return mapToResponseDTO(savedPerformance);
	}

	
	private PerformanceResponseDTO mapToResponseDTO(PlayerPerformance performance) {
	    PerformanceResponseDTO response = new PerformanceResponseDTO();
	    
	    
	    response.setPerformanceId(performance.getPerformanceId());
	    response.setMatchId(performance.getMatch().getMatchId());
	    response.setRunsScored(performance.getRunsScored());

	    response.setPlayerName(performance.getPlayer().getPlayerName());
	    response.setTeamName(performance.getPlayer().getTeam().getTeamName());
	    
	    return response;
	}


	@Override
	public List<PerformanceResponseDTO> getPerformancesByMatch(Integer matchId) {

		List<PlayerPerformance> performances = performanceRepository.findByMatch_MatchId(matchId);
	    
	    return performances.stream()
	            .map(this::mapToResponseDTO) // This calls your helper method on every single item!
	            .collect(Collectors.toList());
	}

	@Override
	public List<PerformanceResponseDTO> getPerformancesByPlayer(Integer playerId) {

		List<PlayerPerformance> performances = performanceRepository.findByPlayer_PlayerId(playerId);
	    
	    return performances.stream()
	            .map(this::mapToResponseDTO)
	            .collect(Collectors.toList());
	}



	@Override
	public PerformanceResponseDTO updatePerformance(Integer id, PerformanceRequestDTO dto) {

		PlayerPerformance existingPerformance = performanceRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Performance not found"));

	    existingPerformance.setRunsScored(dto.getRunsScored());

	    PlayerPerformance updatedPerformance = performanceRepository.save(existingPerformance);
	    return mapToResponseDTO(updatedPerformance);
	}

	@Override
	public void deletePerformance(Integer id) {
	    PlayerPerformance performance = performanceRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Performance not found"));
	            
	    performanceRepository.delete(performance);
	}


}
