package com.akshay.iplcrickbuzz.dto;

import java.util.List;

public class ScorecardResponseDTO {
	
    private MatchResponseDTO match;

    private List<PerformanceResponseDTO> team1Performances;

    private List<PerformanceResponseDTO> team2Performances;

	public MatchResponseDTO getMatch() {
		return match;
	}

	public void setMatch(MatchResponseDTO match) {
		this.match = match;
	}

	public List<PerformanceResponseDTO> getTeam1Performances() {
		return team1Performances;
	}

	public void setTeam1Performances(List<PerformanceResponseDTO> team1Performances) {
		this.team1Performances = team1Performances;
	}

	public List<PerformanceResponseDTO> getTeam2Performances() {
		return team2Performances;
	}

	public void setTeam2Performances(List<PerformanceResponseDTO> team2Performances) {
		this.team2Performances = team2Performances;
	}
    
    
}
