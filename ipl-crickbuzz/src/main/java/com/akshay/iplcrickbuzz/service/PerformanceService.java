package com.akshay.iplcrickbuzz.service;

import java.util.List;

import com.akshay.iplcrickbuzz.dto.PerformanceRequestDTO;
import com.akshay.iplcrickbuzz.dto.PerformanceResponseDTO;

public interface PerformanceService {
	
	PerformanceResponseDTO addPerformance(PerformanceRequestDTO dto);
	
	List<PerformanceResponseDTO> getPerformancesByMatch(Integer matchId);
	
    List<PerformanceResponseDTO> getPerformancesByPlayer(Integer playerId);
    
    PerformanceResponseDTO updatePerformance(Integer id, PerformanceRequestDTO dto);
    
    void deletePerformance(Integer id);
}
