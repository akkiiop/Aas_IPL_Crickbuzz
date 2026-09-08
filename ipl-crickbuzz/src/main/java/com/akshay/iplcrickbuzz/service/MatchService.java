package com.akshay.iplcrickbuzz.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.akshay.iplcrickbuzz.dto.MatchRequestDTO;
import com.akshay.iplcrickbuzz.dto.MatchResponseDTO;
import com.akshay.iplcrickbuzz.dto.ScorecardResponseDTO;

public interface MatchService {
	MatchResponseDTO saveMatch(MatchRequestDTO request);
	
	Page<MatchResponseDTO> getAllMatches(Pageable pageable);
	
	MatchResponseDTO getMatchById(Integer id);
	
	MatchResponseDTO updateMatch(Integer id, MatchRequestDTO request);
	
	void deleteMatch(Integer id);
	
	List<MatchResponseDTO> getMatchesByStatus(String status);
	
	ScorecardResponseDTO getScorecard(Integer matchId);

}
