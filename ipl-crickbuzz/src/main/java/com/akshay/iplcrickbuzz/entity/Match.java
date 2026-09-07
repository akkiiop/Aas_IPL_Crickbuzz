package com.akshay.iplcrickbuzz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;


@Entity
@Table(name = "matches")
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer matchId;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "team1_id")
	private Team team1;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "team2_id")
	private Team team2;
	
	@NotBlank(message = "Value is required")
	private String venue;
	
	@NotNull(message = "Match date is required")
	private LocalDate matchDate;
	
	@ManyToOne
	@JoinColumn(name = "toss_winner_id")
	private Team tossWinner;
	
	private String tossDecision;
	
	@ManyToOne
	@JoinColumn(name = "winner_id")
	private Team winner;
	
	private String status = "UPCOMING";

	public Match() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
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

	public Team getTossWinner() {
		return tossWinner;
	}

	public void setTossWinner(Team tossWinner) {
		this.tossWinner = tossWinner;
	}

	public String getTossDecision() {
		return tossDecision;
	}

	public void setTossDecision(String tossDecision) {
		this.tossDecision = tossDecision;
	}

	public Team getWinner() {
		return winner;
	}

	public void setWinner(Team winner) {
		this.winner = winner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
