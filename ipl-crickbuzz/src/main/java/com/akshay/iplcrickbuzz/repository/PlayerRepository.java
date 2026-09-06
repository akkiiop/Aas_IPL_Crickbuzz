package com.akshay.iplcrickbuzz.repository;


import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.iplcrickbuzz.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findByPlayerNameContainingIgnoreCase(String playerName);

    List<Player> findByTeam_TeamId(Integer teamId);
    
    @Query("SELECT COALESCE(SUM(p.runs), 0) FROM Player p")
    Long getTotalRuns();

    @Query("SELECT COALESCE(SUM(p.wickets), 0) FROM Player p")
    Long getTotalWickets();

    @Query("SELECT p FROM Player p ORDER BY p.runs DESC")
    List<Player> findTopRunScorer();

    @Query("SELECT p FROM Player p ORDER BY p.wickets DESC")
    List<Player> findTopWicketTaker();
}