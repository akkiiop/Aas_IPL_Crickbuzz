package com.akshay.iplcrickbuzz.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PlayerPerformance {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer perfromanceId;
	
	@ManyToOne
	@JoinColumn(name = "match_id")
	private Match match;
	
	@ManyToOne
	@JoinColumn(name = "palyer_id")
	private Player player;
	
	private Integer runsScored;
	
	private Integer ballsFaced;
	
	private Integer wicketsTaken;
	
	private Double oversBowled;
	
	private Integer runsConceded;
	
	private Integer catches;

	public Integer getPerfromanceId() {
		return perfromanceId;
	}

	public void setPerfromanceId(Integer perfromanceId) {
		this.perfromanceId = perfromanceId;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
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

	public Integer getPerformanceId() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
