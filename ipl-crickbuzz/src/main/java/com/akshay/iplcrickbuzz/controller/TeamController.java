package com.akshay.iplcrickbuzz.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.akshay.iplcrickbuzz.dto.TeamRequestDTO;
import com.akshay.iplcrickbuzz.service.TeamService;

import jakarta.validation.Valid;

import com.akshay.iplcrickbuzz.dto.TeamResponseDTO;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
	
	private final TeamService teamService;
	public TeamController(TeamService teamService) {
		this.teamService = teamService;
	}
	
	
	@GetMapping
	public ResponseEntity<List<TeamResponseDTO>> getAllTeams(){
		
		List<TeamResponseDTO> teams = teamService.getAllTeams();
		return ResponseEntity.ok(teams);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<TeamResponseDTO> getTeamById(
			@PathVariable Integer id){
		
		TeamResponseDTO team = teamService.getTeamById(id);
		
		return ResponseEntity.ok(team);
	}
	
	
	@PostMapping
	public ResponseEntity<TeamResponseDTO> saveTeam(
			@Valid @RequestBody TeamRequestDTO dto){
		TeamResponseDTO savedTeam = teamService.saveTeam(dto);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(savedTeam);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTeam(
	        @PathVariable Integer id) {

	    teamService.deleteTeam(id);

	    return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TeamResponseDTO> updateTeam(
	        @PathVariable Integer id,
	        @Valid @RequestBody TeamRequestDTO dto) {

	    TeamResponseDTO updatedTeam =
	            teamService.updateTeam(id, dto);

	    return ResponseEntity.ok(updatedTeam);
	}
	
}
