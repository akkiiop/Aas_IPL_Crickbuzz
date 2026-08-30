package com.akshay.iplcrickbuzz.controller;

import com.akshay.iplcrickbuzz.dto.PlayerRequestDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;
import jakarta.validation.Valid;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public ResponseEntity<Page<PlayerResponseDTO>> getAllPlayers(
	        Pageable pageable) {

	    Page<PlayerResponseDTO> players =
	            playerService.getAllPlayers(pageable);

	    return ResponseEntity.ok(players);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PlayerResponseDTO> getPlayerById(
	        @PathVariable Integer id) {

	    PlayerResponseDTO player =
	            playerService.getPlayerById(id);

	    return ResponseEntity.ok(player);
	}
	
	
	@PostMapping
	public ResponseEntity<PlayerResponseDTO> savePlayer(
			@Valid @RequestBody PlayerRequestDTO dto){
		PlayerResponseDTO savedPlayer = playerService.savePlayer(dto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(savedPlayer);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePlayer(
	        @PathVariable Integer id) {

	    playerService.deletePlayer(id);

	    return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PlayerResponseDTO> updatePlayer(
	        @PathVariable Integer id,
	        @Valid @RequestBody PlayerRequestDTO dto) {

	    PlayerResponseDTO updatedPlayer =
	            playerService.updatePlayer(id, dto);

	    return ResponseEntity.ok(updatedPlayer);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<PlayerResponseDTO>> searchPlayers(
	        @RequestParam("name") String name) {

	    List<PlayerResponseDTO> players =
	            playerService.searchPlayers(name);

	    return ResponseEntity.ok(players);
	}
}
