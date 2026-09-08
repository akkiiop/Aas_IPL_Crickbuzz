package com.akshay.iplcrickbuzz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akshay.iplcrickbuzz.dto.PlayerComparisonDTO;
import com.akshay.iplcrickbuzz.dto.PlayerStatsDTO;
import com.akshay.iplcrickbuzz.dto.TeamStatsDTO;
import com.akshay.iplcrickbuzz.entity.Player;
import com.akshay.iplcrickbuzz.entity.PlayerPerformance;
import com.akshay.iplcrickbuzz.entity.Team;
import com.akshay.iplcrickbuzz.repository.MatchRepository;
import com.akshay.iplcrickbuzz.repository.PerformanceRepository;
import com.akshay.iplcrickbuzz.repository.PlayerRepository;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@Service
public class AnalyticsServiceImpl implements AnalyticsService{
	
	
	@Autowired
	private TeamRepository teamRepository;

	@Autowired
	private MatchRepository matchRepository; // (You might already have this one!)

	@Autowired
	private PerformanceRepository performanceRepository;

	@Autowired
	private PlayerRepository playerRepository;
	
	@Override
	public PlayerStatsDTO getPlayerStats(Integer playerId) {
	    // 1. Check if player exists and get their details
	    Player player = playerRepository.findById(playerId)
	            .orElseThrow(() -> new RuntimeException("Player not found"));

	    // 2. Get ALL their raw performances from the database
	    List<PlayerPerformance> performances = performanceRepository.findByPlayer_PlayerId(playerId);

	    // 3. Prepare our variables
	    int totalRuns = 0, totalBalls = 0, highestScore = 0;
	    int totalWickets = 0, totalRunsConceded = 0;
	    double totalOvers = 0.0;
	    int catches = 0;

	    // 4. The Loop: Add everything up!
	    for (PlayerPerformance p : performances) {
	        totalRuns += p.getRunsScored();
	        totalBalls += p.getBallsFaced();
	        totalWickets += p.getWicketsTaken();
	        totalOvers += p.getOversBowled();
	        totalRunsConceded += p.getRunsConceded();
	        catches += p.getCatches();

	        // Check for highest score
	        if (p.getRunsScored() > highestScore) {
	            highestScore = p.getRunsScored();
	        }
	    }

	    // 5. Build our Response DTO
	    PlayerStatsDTO stats = new PlayerStatsDTO();
	    stats.setPlayerId(player.getPlayerId());
	    stats.setPlayerName(player.getPlayerName());
	    stats.setTeamName(player.getTeam().getTeamName());
	    stats.setMatchesPlayed(performances.size()); // The size of the list is how many matches they played!
	    
	    stats.setTotalRuns(totalRuns);
	    stats.setTotalBallsFaced(totalBalls);
	    stats.setHighestScore(highestScore);
	    stats.setTotalWickets(totalWickets);
	    stats.setTotalOversBowled(totalOvers);
	    stats.setTotalRunsConceded(totalRunsConceded);
	    stats.setTotalCatches(catches);

	    // 6. Calculate Averages (Protect against dividing by zero!)
	    if (performances.size() > 0) {
	        stats.setBattingAverage((double) totalRuns / performances.size());
	    }
	    if (totalBalls > 0) {
	        stats.setStrikeRate(((double) totalRuns / totalBalls) * 100);
	    }
	    if (totalOvers > 0) {
	        stats.setEconomyRate(totalRunsConceded / totalOvers);
	    }

	    return stats;
	}
	
	@Override
	public TeamStatsDTO getTeamStats(Integer teamId) {
	    // 1. Get the Team from the database
	    Team team = teamRepository.findById(teamId)
	            .orElseThrow(() -> new RuntimeException("Team not found"));

	    // 2. Ask the database to count the matches for us!
	    long matchesPlayed = matchRepository.countByTeam1OrTeam2(team, team);
	    long wins = matchRepository.countByWinner(team);
	    long losses = matchesPlayed - wins;

	    // 3. Put it in our Box (DTO)
	    TeamStatsDTO stats = new TeamStatsDTO();
	    stats.setTeamId(team.getTeamId());
	    stats.setTeamName(team.getTeamName());
	    stats.setMatchesPlayed(matchesPlayed);
	    stats.setWins(wins);
	    stats.setLosses(losses);

	    // 4. Calculate Win Percentage (Avoid dividing by zero!)
	    if (matchesPlayed > 0) {
	        double winPercentage = ((double) wins / matchesPlayed) * 100;
	        stats.setWinPercentage(winPercentage);
	    } else {
	        stats.setWinPercentage(0.0);
	    }

	    return stats;
	}
	
	@Override
	public PlayerComparisonDTO comparePlayers(Integer player1Id, Integer player2Id) {
	    PlayerStatsDTO p1Stats = this.getPlayerStats(player1Id);
	    
	    PlayerStatsDTO p2Stats = this.getPlayerStats(player2Id);
	    
	    PlayerComparisonDTO comparison = new PlayerComparisonDTO();
	    comparison.setPlayer1Stats(p1Stats);
	    comparison.setPlayer2Stats(p2Stats);
	    
	    return comparison;
	}



}
