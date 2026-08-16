package com.akshay.iplcrickbuzz.service;
import java.util.List;
import com.akshay.iplcrickbuzz.entity.Player;
public interface PlayerService {
	Player savePlayer(Player player);
	List<Player> getAllPlayers();
	Player getPlayerById(Integer id);
	void deletePlayer(Integer id);
}
