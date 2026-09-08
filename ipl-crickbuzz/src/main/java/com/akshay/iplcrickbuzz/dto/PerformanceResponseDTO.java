package com.akshay.iplcrickbuzz.dto;

public class PerformanceResponseDTO {
	private Integer performanceId; 
    private Integer matchId;       
    private String playerName;     
    private String teamName;       
    private Integer runsScored;
    private Integer ballsFaced;
    private Integer wicketsTaken;
    private Double oversBowled;
    private Integer runsConceded;
    private Integer catches;
    
    
	public Integer getPerformanceId() {
		return performanceId;
	}
	public void setPerformanceId(Integer performanceId) {
		this.performanceId = performanceId;
	}
	public Integer getMatchId() {
		return matchId;
	}
	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public Integer getRunsScored() {
		return runsScored;
	}
	public void setRunsScored(Integer runsScored) {
		this.runsScored = runsScored;
	}
	public Integer getBallsFaced() {
		return ballsFaced;
	}
	public void setBallsFaced(Integer ballsFaced) {
		this.ballsFaced = ballsFaced;
	}
	public Integer getWicketsTaken() {
		return wicketsTaken;
	}
	public void setWicketsTaken(Integer wicketsTaken) {
		this.wicketsTaken = wicketsTaken;
	}
	public Double getOversBowled() {
		return oversBowled;
	}
	public void setOversBowled(Double oversBowled) {
		this.oversBowled = oversBowled;
	}
	public Integer getRunsConceded() {
		return runsConceded;
	}
	public void setRunsConceded(Integer runsConceded) {
		this.runsConceded = runsConceded;
	}
	public Integer getCatches() {
		return catches;
	}
	public void setCatches(Integer catches) {
		this.catches = catches;
	}
    
    
}
