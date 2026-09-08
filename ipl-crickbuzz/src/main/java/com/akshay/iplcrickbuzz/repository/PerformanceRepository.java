package com.akshay.iplcrickbuzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import com.akshay.iplcrickbuzz.entity.Player;

import com.akshay.iplcrickbuzz.entity.PlayerPerformance;

public interface PerformanceRepository extends JpaRepository<PlayerPerformance, Integer> {
	
	List<PlayerPerformance> findByMatch_MatchId(Integer matchId);
	
	List<PlayerPerformance> findByPlayer_PlayerId(Integer playerId);
	
	@Query("SELECT p.player FROM PlayerPerformance p GROUP BY p.player ORDER BY SUM(p.runsScored) DESC")
	List<Player> findTopRunScorerFromPerformances(Pageable pageable);
	// Find Top Wicket Taker
	@Query("SELECT p.player FROM PlayerPerformance p GROUP BY p.player ORDER BY SUM(p.wicketsTaken) DESC")
	List<Player> findTopWicketTakerFromPerformances(Pageable pageable);

}
