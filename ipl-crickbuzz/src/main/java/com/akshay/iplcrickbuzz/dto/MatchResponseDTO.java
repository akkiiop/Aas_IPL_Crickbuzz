package com.akshay.iplcrickbuzz.dto;

import java.time.LocalDate;

public class MatchResponseDTO {
	  private Integer matchId;
	    
	    private Integer team1Id;
	    private String team1Name;
	    
	    private Integer team2Id;
	    private String team2Name;
	    
	    private String venue;
	    private LocalDate matchDate;
	    
	    private String tossWinnerName;
	    private String tossDecision;
	    
	    private String winnerName;
	    private String status;
		public Integer getMatchId() {
			return matchId;
		}
		public void setMatchId(Integer matchId) {
			this.matchId = matchId;
		}
		public Integer getTeam1Id() {
			return team1Id;
		}
		public void setTeam1Id(Integer team1Id) {
			this.team1Id = team1Id;
		}
		public String getTeam1Name() {
			return team1Name;
		}
		public void setTeam1Name(String team1Name) {
			this.team1Name = team1Name;
		}
		public Integer getTeam2Id() {
			return team2Id;
		}
		public void setTeam2Id(Integer team2Id) {
			this.team2Id = team2Id;
		}
		public String getTeam2Name() {
			return team2Name;
		}
		public void setTeam2Name(String team2Name) {
			this.team2Name = team2Name;
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
		public String getTossWinnerName() {
			return tossWinnerName;
		}
		public void setTossWinnerName(String tossWinnerName) {
			this.tossWinnerName = tossWinnerName;
		}
		public String getTossDecision() {
			return tossDecision;
		}
		public void setTossDecision(String tossDecision) {
			this.tossDecision = tossDecision;
		}
		public String getWinnerName() {
			return winnerName;
		}
		public void setWinnerName(String winnerName) {
			this.winnerName = winnerName;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	    
	    
}
