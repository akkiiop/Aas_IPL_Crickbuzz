package com.akshay.iplcrickbuzz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Player {
	
	@Id
	private int playerId;
	private int jerseyNumber;
	private String playerName;
	private int runs;
	private int wickets;
	private String teamName;
	private String specialization;
	
	public Player() {
		
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

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

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	@Override
	public String toString() {
		return "Player [playerId=" + playerId + ", jerseyNumber=" + jerseyNumber + ", playerName=" + playerName
				+ ", runs=" + runs + ", wickets=" + wickets + ", teamName=" + teamName + ", specialization="
				+ specialization + ", getPlayerId()=" + getPlayerId() + ", getJerseyNumber()=" + getJerseyNumber()
				+ ", getPlayerName()=" + getPlayerName() + ", getRuns()=" + getRuns() + ", getWickets()=" + getWickets()
				+ ", getTeamName()=" + getTeamName() + ", getSpecialization()=" + getSpecialization() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
