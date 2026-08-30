package com.akshay.iplcrickbuzz.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class PlayerRequestDTO {
	@Min(value = 1, message = "Jersey number must be at least  1")
	@Max(value = 999, message = "Jersey number must not exceed 999")
	@NotNull(message = "Jersey number is required")
	private Integer jerseyNumber;
	
	@NotBlank(message = "Player name is required")
	private String playerName;
	
	@NotNull(message = "Runs are required")
    @PositiveOrZero(message = "Runs cannot be negative")
	private Integer runs;
	
	@NotNull(message = "Wickets are required")
    @PositiveOrZero(message = "Wickets cannot be negative")
	private Integer wickets;
	
	@NotNull(message = "Team ID is required")
    @Positive(message = "Team ID must be greater than 0")
	private Integer teamId;
	
	@NotBlank(message = "Specialization is required")
	private String specialization;
	

	public int getJerseyNumber() {
		return jerseyNumber;
	}

	public void setJerseyNumber(int jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getRuns() {
		return runs;
	}

	public void setRuns(int runs) {
		this.runs = runs;
	}

	public int getWickets() {
		return wickets;
	}

	public void setWickets(int wickets) {
		this.wickets = wickets;
	}

	

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public void setJerseyNumber(Integer jerseyNumber) {
		this.jerseyNumber = jerseyNumber;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
}
