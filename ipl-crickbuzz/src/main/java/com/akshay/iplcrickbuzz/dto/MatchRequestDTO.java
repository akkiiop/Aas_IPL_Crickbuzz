package com.akshay.iplcrickbuzz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class MatchRequestDTO {
	@NotNull(message = "Team 1 ID is required")
    private Integer team1Id;
	
    @NotNull(message = "Team 2 ID is required")
    private Integer team2Id;
    
    @NotBlank(message = "Venue is required")
    private String venue;
    
    @NotNull(message = "Match date is required")
    private LocalDate matchDate;
    
    private Integer tossWinnerId;
    private String tossDecision;
    private Integer winnerId;
    private String status = "UPCOMING";
    
	public Integer getTeam1Id() {
		return team1Id;
	}
	public void setTeam1Id(Integer team1Id) {
		this.team1Id = team1Id;
	}
	public Integer getTeam2Id() {
		return team2Id;
	}
	public void setTeam2Id(Integer team2Id) {
		this.team2Id = team2Id;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public LocalDate getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(LocalDate matchDate) {
		this.matchDate = matchDate;
	}
	public Integer getTossWinnerId() {
		return tossWinnerId;
	}
	public void setTossWinnerId(Integer tossWinnerId) {
		this.tossWinnerId = tossWinnerId;
	}
	public String getTossDecision() {
		return tossDecision;
	}
	public void setTossDecision(String tossDecision) {
		this.tossDecision = tossDecision;
	}
	public Integer getWinnerId() {
		return winnerId;
	}
	public void setWinnerId(Integer winnerId) {
		this.winnerId = winnerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
