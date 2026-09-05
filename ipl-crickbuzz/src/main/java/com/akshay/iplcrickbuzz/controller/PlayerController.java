package com.akshay.iplcrickbuzz.controller;

import com.akshay.iplcrickbuzz.dto.PlayerRequestDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;
import com.akshay.iplcrickbuzz.service.PlayerService;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/players")
@Tag(
        name = "Players",
        description = "APIs for managing IPL players"
)
public class PlayerController {

    private final PlayerService playerService;

    // Constructor Injection
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }


    // =====================================================
    // GET ALL PLAYERS - PAGINATION
    // =====================================================

    @Operation(
            summary = "Get all players",
            description = "Returns a paginated and sortable list of IPL players"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Players retrieved successfully"
            )
    })
    @GetMapping
    public ResponseEntity<Page<PlayerResponseDTO>> getAllPlayers(
            Pageable pageable) {

        Page<PlayerResponseDTO> players =
                playerService.getAllPlayers(pageable);

        return ResponseEntity.ok(players);
    }


    // =====================================================
    // SEARCH PLAYERS BY NAME
    // =====================================================

    @Operation(
            summary = "Search players",
            description = "Searches IPL players by name"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Search completed successfully"
            )
    })
    @GetMapping("/search")
    public ResponseEntity<List<PlayerResponseDTO>> searchPlayers(
            @Parameter(
                    description = "Player name or part of the player name",
                    example = "Virat"
            )
            @RequestParam("name") String name) {

        List<PlayerResponseDTO> players =
                playerService.searchPlayers(name);

        return ResponseEntity.ok(players);
    }
    
    
    
    @Operation(
    	    summary = "Get players by team",
    	    description = "Returns all IPL players belonging to a specific team"
    	)
    	@ApiResponses({
    	    @ApiResponse(
    	        responseCode = "200",
    	        description = "Players retrieved successfully"
    	    )
    	})
    	@GetMapping("/team/{teamId}")
    	public ResponseEntity<List<PlayerResponseDTO>> getPlayersByTeam(
    	        @Parameter(
    	            description = "ID of the team",
    	            example = "9"
    	        )
    	        @PathVariable Integer teamId) {

    	    List<PlayerResponseDTO> players =
    	            playerService.getPlayersByTeam(teamId);

    	    return ResponseEntity.ok(players);
    	}

    // =====================================================
    // GET PLAYER BY ID
    // =====================================================

    @Operation(
            summary = "Get player by ID",
            description = "Returns a single IPL player using the player ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Player found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> getPlayerById(
            @Parameter(
                    description = "ID of the player",
                    example = "1"
            )
            @PathVariable Integer id) {

        PlayerResponseDTO player =
                playerService.getPlayerById(id);

        return ResponseEntity.ok(player);
    }


    // =====================================================
    // CREATE PLAYER
    // =====================================================

    @Operation(
            summary = "Create a player",
            description = "Creates a new IPL player"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Player created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid player data"
            )
    })
    @PostMapping
    public ResponseEntity<PlayerResponseDTO> savePlayer(
            @Valid @RequestBody PlayerRequestDTO dto) {

        PlayerResponseDTO savedPlayer =
                playerService.savePlayer(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedPlayer);
    }


    // =====================================================
    // UPDATE PLAYER
    // =====================================================

    @Operation(
            summary = "Update a player",
            description = "Updates an existing IPL player's information"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Player updated successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player not found"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid player data"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponseDTO> updatePlayer(
            @PathVariable Integer id,
            @Valid @RequestBody PlayerRequestDTO dto) {

        PlayerResponseDTO updatedPlayer =
                playerService.updatePlayer(id, dto);

        return ResponseEntity.ok(updatedPlayer);
    }


    // =====================================================
    // DELETE PLAYER
    // =====================================================

    @Operation(
            summary = "Delete a player",
            description = "Deletes an IPL player using the player ID"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Player deleted successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Player not found"
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(
            @PathVariable Integer id) {

        playerService.deletePlayer(id);

        return ResponseEntity.noContent().build();
    }
}