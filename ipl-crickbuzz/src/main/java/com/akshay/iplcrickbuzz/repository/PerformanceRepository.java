package com.akshay.iplcrickbuzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.iplcrickbuzz.entity.PlayerPerformance;

public interface PerformanceRepository extends JpaRepository<PlayerPerformance, Integer> {
	
	List<PlayerPerformance> findByMatch_MatchId(Integer matchId);
	
	List<PlayerPerformance> findByPlayer_PlayerId(Integer playerId);

}
