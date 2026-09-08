package com.akshay.iplcrickbuzz.dto;

public class DashboardResponseDTO {

    private long totalTeams;
    private long totalPlayers;
    private long totalRuns;
    private long totalWickets;
    
    private PlayerResponseDTO topRunScorer;
    private PlayerResponseDTO topWicketTaker;
    private long totalMatches;

    public DashboardResponseDTO() {
    }

    public DashboardResponseDTO(
            long totalTeams,
            long totalPlayers,
            long totalRuns,
            long totalWickets,
            PlayerResponseDTO topRunScorer,
            PlayerResponseDTO topWicketTaker) {
    															
        this.totalTeams = totalTeams;
        this.totalPlayers = totalPlayers;
        this.totalRuns = totalRuns;
        this.totalWickets = totalWickets;
        this.topRunScorer = topRunScorer;
        this.topWicketTaker = topWicketTaker;
    }

    public long getTotalTeams() {
        return totalTeams;
    }

    public void setTotalTeams(long totalTeams) {
        this.totalTeams = totalTeams;
    }

    public long getTotalPlayers() {
        return totalPlayers;
    }

    public void setTotalPlayers(long totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public long getTotalRuns() {
        return totalRuns;
    }

    public void setTotalRuns(long totalRuns) {
        this.totalRuns = totalRuns;
    }

    public long getTotalWickets() {
        return totalWickets;
    }

    public void setTotalWickets(long totalWickets) {
        this.totalWickets = totalWickets;
    }

    public PlayerResponseDTO getTopRunScorer() {
        return topRunScorer;
    }

    public void setTopRunScorer(
            PlayerResponseDTO topRunScorer) {
        this.topRunScorer = topRunScorer;
    }

    public PlayerResponseDTO getTopWicketTaker() {
        return topWicketTaker;
    }

    public void setTopWicketTaker(
            PlayerResponseDTO topWicketTaker) {
        this.topWicketTaker = topWicketTaker;
    }

	public long getTotalMatches() {
		return totalMatches;
	}

	public void setTotalMatches(long totalMatches) {
		this.totalMatches = totalMatches;
	}
    
    
}