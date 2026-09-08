package com.akshay.iplcrickbuzz.controller;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.akshay.iplcrickbuzz.dto.MatchRequestDTO;
import com.akshay.iplcrickbuzz.dto.MatchResponseDTO;
import com.akshay.iplcrickbuzz.dto.ScorecardResponseDTO;
import com.akshay.iplcrickbuzz.service.MatchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/matches")
@Tag(name = "Matche APIs", description = "Create, read, update, and delete matches")
public class MatchController {
	
	private final MatchService matchService;
	
	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}
	
	@PostMapping
	@Operation(summary = "Create a new match")
	public ResponseEntity<MatchResponseDTO> createMatch(@Valid @RequestBody MatchRequestDTO request){
		MatchResponseDTO response = matchService.saveMatch(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping
    @Operation(summary = "Get all matches with pagination")
    public ResponseEntity<Page<MatchResponseDTO>> getAllMatches(Pageable pageable) {
        Page<MatchResponseDTO> response = matchService.getAllMatches(pageable);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
    @GetMapping("/{id}")
    @Operation(summary = "Get match by ID")
    public ResponseEntity<MatchResponseDTO> getMatchById(@PathVariable Integer id) {
        MatchResponseDTO response = matchService.getMatchById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update an existing match")
    public ResponseEntity<MatchResponseDTO> updateMatch(
            @PathVariable Integer id, 
            @Valid @RequestBody MatchRequestDTO request) {
        
        MatchResponseDTO response = matchService.updateMatch(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a match")
    public ResponseEntity<Void> deleteMatch(@PathVariable Integer id) {
        matchService.deleteMatch(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Get matches by status (UPCOMING, LIVE, COMPLETED)")
    public ResponseEntity<List<MatchResponseDTO>> getMatchesByStatus(@PathVariable String status) {
        List<MatchResponseDTO> response = matchService.getMatchesByStatus(status);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @GetMapping("/{id}/scorecard")
    public ResponseEntity<ScorecardResponseDTO> getMatchScorecard(@PathVariable Integer id) {
        ScorecardResponseDTO scorecard = matchService.getScorecard(id);
        return ResponseEntity.ok(scorecard);
    }


}
