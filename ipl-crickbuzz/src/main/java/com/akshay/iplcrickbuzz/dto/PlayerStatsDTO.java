package com.akshay.iplcrickbuzz.dto;

public class PlayerStatsDTO {
	private Integer playerId;
    private String playerName;
    private String teamName;
    
    // Aggregate Batting Stats
    private long matchesPlayed;
    private int totalRuns;
    private int totalBallsFaced;
    private int highestScore;
    private double battingAverage;
    private double strikeRate;
    
    // Aggregate Bowling Stats
    private int totalWickets;
    private double totalOversBowled;
    private int totalRunsConceded;
    private double economyRate;
    
    // Fielding Stats
    private int totalCatches;

	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
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

	public long getMatchesPlayed() {
		return matchesPlayed;
	}

	public void setMatchesPlayed(long matchesPlayed) {
		this.matchesPlayed = matchesPlayed;
	}

	public int getTotalRuns() {
		return totalRuns;
	}

	public void setTotalRuns(int totalRuns) {
		this.totalRuns = totalRuns;
	}

	public int getTotalBallsFaced() {
		return totalBallsFaced;
	}

	public void setTotalBallsFaced(int totalBallsFaced) {
		this.totalBallsFaced = totalBallsFaced;
	}

	public int getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}

	public double getBattingAverage() {
		return battingAverage;
	}

	public void setBattingAverage(double battingAverage) {
		this.battingAverage = battingAverage;
	}

	public double getStrikeRate() {
		return strikeRate;
	}

	public void setStrikeRate(double strikeRate) {
		this.strikeRate = strikeRate;
	}

	public int getTotalWickets() {
		return totalWickets;
	}

	public void setTotalWickets(int totalWickets) {
		this.totalWickets = totalWickets;
	}

	public double getTotalOversBowled() {
		return totalOversBowled;
	}

	public void setTotalOversBowled(double totalOversBowled) {
		this.totalOversBowled = totalOversBowled;
	}

	public int getTotalRunsConceded() {
		return totalRunsConceded;
	}

	public void setTotalRunsConceded(int totalRunsConceded) {
		this.totalRunsConceded = totalRunsConceded;
	}

	public double getEconomyRate() {
		return economyRate;
	}

	public void setEconomyRate(double economyRate) {
		this.economyRate = economyRate;
	}

	public int getTotalCatches() {
		return totalCatches;
	}

	public void setTotalCatches(int totalCatches) {
		this.totalCatches = totalCatches;
	}
    
    
}
