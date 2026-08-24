package com.akshay.iplcrickbuzz.service;
import java.util.List;
import org.springframework.stereotype.Service;

import com.akshay.iplcrickbuzz.dto.PlayerRequestDTO;
import com.akshay.iplcrickbuzz.dto.PlayerResponseDTO;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.exception.PlayerNotFoundException;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService{
	 private final PlayerRepository playerRepository;
	 
	 public PlayerServiceImpl(PlayerRepository playerRepository) {
		 this.playerRepository = playerRepository;
	 }
	 
	 
	 private PlayerResponseDTO convertToResponseDTO(Player player) {

		    PlayerResponseDTO dto = new PlayerResponseDTO();

		    dto.setPlayerId(player.getPlayerId());
		    dto.setJerseyNumber(player.getJerseyNumber());
		    dto.setPlayerName(player.getPlayerName());
		    dto.setRuns(player.getRuns());
		    dto.setWickets(player.getWickets());
		    dto.setTeamName(player.getTeamName());
		    dto.setSpecialization(player.getSpecialization());

		    return dto;
		}
	 
	 @Override
	 public PlayerResponseDTO savePlayer(PlayerRequestDTO dto) {
		 Player player = new Player();

		    player.setJerseyNumber(dto.getJerseyNumber());
		    player.setPlayerName(dto.getPlayerName());
		    player.setRuns(dto.getRuns());
		    player.setWickets(dto.getWickets());
		    player.setTeamName(dto.getTeamName());
		    player.setSpecialization(dto.getSpecialization());

		    Player savedPlayer = playerRepository.save(player);

		    return convertToResponseDTO(savedPlayer);
	 }
	 
	 
	 
	 @Override
	 public List<PlayerResponseDTO> getAllPlayers() {

	     List<Player> players = playerRepository.findAll();

	     return players.stream()
	             .map(this::convertToResponseDTO)
	             .toList();
	 }
	 
	 @Override
	 public PlayerResponseDTO getPlayerById(Integer id) {

	     Player player = playerRepository.findById(id)
	             .orElseThrow(() ->
	                     new PlayerNotFoundException(
	                             "Player not found with id: " + id));

	     return convertToResponseDTO(player);
	 }
	 
	 
	 @Override
	 public PlayerResponseDTO updatePlayer(
	         Integer id,
	         PlayerRequestDTO dto) {

	     Player player = playerRepository.findById(id)
	             .orElseThrow(() ->
	                     new PlayerNotFoundException(
	                             "Player not found with id: " + id));

	     player.setJerseyNumber(dto.getJerseyNumber());
	     player.setPlayerName(dto.getPlayerName());
	     player.setRuns(dto.getRuns());
	     player.setWickets(dto.getWickets());
	     player.setTeamName(dto.getTeamName());
	     player.setSpecialization(dto.getSpecialization());

	     Player updatedPlayer = playerRepository.save(player);

	     return convertToResponseDTO(updatedPlayer);
	 }
	 
	 
	 @Override
	 public void deletePlayer(Integer id) {

	     Player player = playerRepository.findById(id)
	             .orElseThrow(() ->
	                     new PlayerNotFoundException(
	                             "Player not found with id: " + id));

	     playerRepository.delete(player);
	 }
}
