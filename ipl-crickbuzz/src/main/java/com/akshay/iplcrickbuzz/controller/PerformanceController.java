package com.akshay.iplcrickbuzz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.akshay.iplcrickbuzz.dto.PerformanceRequestDTO;
import com.akshay.iplcrickbuzz.dto.PerformanceResponseDTO;
import com.akshay.iplcrickbuzz.service.PerformanceService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/performances")
public class PerformanceController {
	@Autowired
    private PerformanceService performanceService;

	@PostMapping
    public ResponseEntity<PerformanceResponseDTO> addPerformance(@RequestBody PerformanceRequestDTO dto) {
        

		PerformanceResponseDTO response = performanceService.addPerformance(dto);
        

		return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
	
	@GetMapping("/match/{matchId}")
	public ResponseEntity<List<PerformanceResponseDTO>> getByMatch(@PathVariable Integer matchId) {
	    return ResponseEntity.ok(performanceService.getPerformancesByMatch(matchId));
	}

	@GetMapping("/player/{playerId}")
	public ResponseEntity<List<PerformanceResponseDTO>> getByPlayer(@PathVariable Integer playerId) {
	    return ResponseEntity.ok(performanceService.getPerformancesByPlayer(playerId));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PerformanceResponseDTO> updatePerformance(
	        @PathVariable Integer id, 
	        @RequestBody PerformanceRequestDTO dto) {
	        
	    return ResponseEntity.ok(performanceService.updatePerformance(id, dto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePerformance(@PathVariable Integer id) {
	    performanceService.deletePerformance(id);
	    // Return 204 No Content because the item is gone and we have nothing to send back
	    return ResponseEntity.noContent().build(); 
	}


}
