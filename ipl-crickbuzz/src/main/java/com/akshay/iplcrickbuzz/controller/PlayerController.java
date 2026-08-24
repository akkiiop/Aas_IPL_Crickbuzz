package com.akshay.iplcrickbuzz.controller;

import com.akshay.iplcrickbuzz.dto.PlayerRequestDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.akshay.iplcrickbuzz.service.PlayerService;

@RestController
@RequestMapping("/api/players")
public class PlayerController {
	private final PlayerService playerService;
	public PlayerController(PlayerService playerService) {
		this.playerService = playerService;
	}
	
	@GetMapping
	public List<PlayerResponseDTO> getAllPlayers() {

	    return playerService.getAllPlayers();
	}
	
	@GetMapping("/{id}")
	public PlayerResponseDTO getPlayerById(
	        @PathVariable Integer id) {

	    return playerService.getPlayerById(id);
	}
	
	
	@PostMapping
	public PlayerResponseDTO savePlayer(
	        @Valid @RequestBody PlayerRequestDTO dto) {

	    return playerService.savePlayer(dto);
	}
	
	
	@DeleteMapping("/{id}")
	public String deletePlayer(@PathVariable Integer id) {
		playerService.deletePlayer(id);
		return "Player deleted Successfully";
	}
	
	@PutMapping("/{id}")
	public PlayerResponseDTO updatePlayer(
	        @PathVariable Integer id,
	        @Valid @RequestBody PlayerRequestDTO dto) {

	    return playerService.updatePlayer(id, dto);
	}
}
