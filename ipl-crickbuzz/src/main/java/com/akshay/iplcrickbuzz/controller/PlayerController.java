package com.akshay.iplcrickbuzz.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.service.PlayerService;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
	private final PlayerService playerService;
	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}
	
	@GetMapping
	public List<Player> getAllPlayers(){
		return playerService.getAllPlayers();
	}
	
	@GetMapping("/{id}")
	public Player getPlayerById(@PathVariable Integer id) {
		return playerService.getPlayerById(id);
	}
	
	
	@PostMapping
	public  Player savePlayer(@RequestBody Player player) {
		return playerService.savePlayer(player);
	}
	
	@DeleteMapping("/{id}")
	public String deletePlayer(@PathVariable Integer id) {
		playerService.deletePlayer(id);
		return "Player deleted Successfully";
	}
	
	@PutMapping
	public Player updatePlayer(@RequestBody Player player) {
		return playerService.updatePlayer(player);
	}
}
