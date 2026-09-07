package com.akshay.iplcrickbuzz.mapper;

import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;import com.akshay.iplcrickbuzz.entity.Player;

public class PlayerMapper {
	public static PlayerResponseDTO toDTO(Player player) {
		
		PlayerResponseDTO dto = new PlayerResponseDTO();
		
		dto.setPlayerId(player.getPlayerId());
		dto.setJerseyNumber(player.getJerseyNumber());
		dto.setPlayerName(player.getPlayerName());
		dto.setRuns(player.getRuns());
		dto.setWickets(player.getWickets());
		dto.setSpecialization(player.getSpecialization());
		
		if(player.getTeam() != null) {
			dto.setTeamName(player.getTeam().getTeamName());
		}
		
		return dto;
	}
}
