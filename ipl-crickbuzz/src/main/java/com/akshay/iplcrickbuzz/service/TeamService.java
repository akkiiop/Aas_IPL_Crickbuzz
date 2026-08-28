package com.akshay.iplcrickbuzz.service;

import java.util.List;

import com.akshay.iplcrickbuzz.dto.TeamRequestDTO;
import com.akshay.iplcrickbuzz.dto.TeamResponseDTO;
import com.akshay.iplcrickbuzz.dto.TeamRequestDTO;
import com.akshay.iplcrickbuzz.dto.TeamResponseDTO;
import com.akshay.iplcrickbuzz.dto.TeamRequestDTO;
import com.akshay.iplcrickbuzz.dto.TeamResponseDTO;
public interface TeamService {
	
	TeamResponseDTO saveTeam(TeamRequestDTO request);
	
	List<TeamResponseDTO> getAllTeams();
	
	TeamResponseDTO getTeamById(Integer id);
	
	TeamResponseDTO updateTeam(Integer id, TeamRequestDTO request);
	
	void deleteTeam(Integer id);
}
