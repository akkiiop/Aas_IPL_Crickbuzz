package com.akshay.iplcrickbuzz.service;
import java.util.List;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;

import com.akshay.iplcrickbuzz.dto.PlayerRequestDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;

public interface PlayerService {
	PlayerResponseDTO savePlayer(PlayerRequestDTO dto);
	
	Page<PlayerResponseDTO> getAllPlayers(org.springframework.data.domain.Pageable pageable);
	
	List<PlayerResponseDTO> searchPlayers(String name);
	
	List<PlayerResponseDTO> getPlayersByTeam(Integer teamId);
	
	PlayerResponseDTO getPlayerById(Integer id);
	
	
	void deletePlayer(Integer id);

	PlayerResponseDTO updatePlayer(Integer id, PlayerRequestDTO dto);
	
	
}
