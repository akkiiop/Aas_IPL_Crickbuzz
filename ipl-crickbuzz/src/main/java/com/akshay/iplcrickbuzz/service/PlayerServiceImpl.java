package com.akshay.iplcrickbuzz.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService{
	 private final PlayerRepository playerRepository;
	 
	 public PlayerServiceImpl(PlayerRepository playerRepository) {
		 this.playerRepository = playerRepository;
	 }
	 
	 @Override
	 public Player savePlayer(Player player) {
		 return playerRepository.save(player);
	 }
	 
	 @Override
	 public List<Player> getAllPlayers(){
		 return playerRepository.findAll();
	 }
	 
	 @Override
	 public Player getPlayerById(Integer id) {
		 return playerRepository.findById(id).orElse(null);
	 }
	 
	 @Override 
	 public void deletePlayer(Integer id) {
		 playerRepository.deleteById(id);
	 }
}
