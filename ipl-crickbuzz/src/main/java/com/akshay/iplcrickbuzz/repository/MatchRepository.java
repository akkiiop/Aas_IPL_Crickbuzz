package com.akshay.iplcrickbuzz.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akshay.iplcrickbuzz.entity.Match;
import com.akshay.iplcrickbuzz.entity.Team;

public interface MatchRepository extends JpaRepository<Match, Integer> {
	
	List<Match> findByStatus(String status);
	
	List<Match> findByTeam1OrTeam2(Team team1, Team team2);
	
	Long countByWinner(Team winner);
	Long countByteam1OrTeam2(Team team1, Team team2);

}
