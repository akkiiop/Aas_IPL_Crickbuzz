package com.akshay.iplcrickbuzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.akshay.iplcrickbuzz.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    List<Player> findByPlayerNameContainingIgnoreCase(String playerName);

    List<Player> findByTeam_TeamId(Integer teamId);
}