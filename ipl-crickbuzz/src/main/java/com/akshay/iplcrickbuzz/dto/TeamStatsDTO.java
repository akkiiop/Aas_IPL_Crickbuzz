package com.akshay.iplcrickbuzz.dto;

public class TeamStatsDTO {
	private Integer teamId;
    private String teamName;
    
    private long matchesPlayed;
    private long wins;
    private long losses;
    private double winPercentage;
    
    private int totalRunsScoredByTeam;
    private int totalWicketsTakenByTeam;
	public Integer getTeamId() {
		return teamId;
	}
	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public long getMatchesPlayed() {
		return matchesPlayed;
	}
	public void setMatchesPlayed(long matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}
	public long getWins() {
		return wins;
	}
	public void setWins(long wins) {
		this.wins = wins;
	}
	public long getLosses() {
		return losses;
	}
	public void setLosses(long losses) {
		this.losses = losses;
	}
	public double getWinPercentage() {
		return winPercentage;
	}
	public void setWinPercentage(double winPercentage) {
		this.winPercentage = winPercentage;
	}
	public int getTotalRunsScoredByTeam() {
		return totalRunsScoredByTeam;
	}
	public void setTotalRunsScoredByTeam(int totalRunsScoredByTeam) {
		this.totalRunsScoredByTeam = totalRunsScoredByTeam;
	}
	public int getTotalWicketsTakenByTeam() {
		return totalWicketsTakenByTeam;
	}
	public void setTotalWicketsTakenByTeam(int totalWicketsTakenByTeam) {
		this.totalWicketsTakenByTeam = totalWicketsTakenByTeam;
	}
    
    
}
