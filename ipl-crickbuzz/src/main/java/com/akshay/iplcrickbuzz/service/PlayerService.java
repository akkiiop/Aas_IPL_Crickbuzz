package com.akshay.iplcrickbuzz.service;
import java.util.List;

import com.akshay.iplcrickbuzz.dto.PlayerRequestDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;

public interface PlayerService {
	PlayerResponseDTO savePlayer(PlayerRequestDTO dto);
	
	List<PlayerResponseDTO> getAllPlayers();
	
	PlayerResponseDTO getPlayerById(Integer id);
	
	
	
	void deletePlayer(Integer id);

	PlayerResponseDTO updatePlayer(Integer id, PlayerRequestDTO dto);
}
