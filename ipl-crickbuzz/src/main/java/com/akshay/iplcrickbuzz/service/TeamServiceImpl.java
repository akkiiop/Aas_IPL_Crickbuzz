package com.akshay.iplcrickbuzz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.akshay.iplcrickbuzz.dto.TeamRequestDTO;
import com.akshay.iplcrickbuzz.dto.TeamResponseDTO;
import com.akshay.iplcrickbuzz.entity.Team;
import com.akshay.iplcrickbuzz.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    public TeamServiceImpl(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    // =========================
    // CREATE TEAM
    // =========================
    @Override
    public TeamResponseDTO saveTeam(TeamRequestDTO request) {

        Team team = new Team();

        team.setTeamName(request.getTeamName());
        team.setShortName(request.getShortName());
        team.setCity(request.getCity());
        team.setCaptain(request.getCaptain());
        team.setHomeGround(request.getHomeGround());

        Team savedTeam = teamRepository.save(team);

        return convertToResponseDTO(savedTeam);
    }


    // =========================
    // GET ALL TEAMS
    // =========================
    @Override
    public List<TeamResponseDTO> getAllTeams() {

        List<Team> teams = teamRepository.findAll();

        return teams.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    // =========================
    // GET TEAM BY ID
    // =========================
    @Override
    public TeamResponseDTO getTeamById(Integer id) {

        Team team = teamRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Team not found with id: " + id)
                );

        return convertToResponseDTO(team);
    }


    // =========================
    // UPDATE TEAM
    // =========================
    @Override
    public TeamResponseDTO updateTeam(
            Integer id,
            TeamRequestDTO request) {

        Team team = teamRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Team not found with id: " + id)
                );

        team.setTeamName(request.getTeamName());
        team.setShortName(request.getShortName());
        team.setCity(request.getCity());
        team.setCaptain(request.getCaptain());
        team.setHomeGround(request.getHomeGround());

        Team updatedTeam = teamRepository.save(team);

        return convertToResponseDTO(updatedTeam);
    }


    // =========================
    // DELETE TEAM
    // =========================
    @Override
    public void deleteTeam(Integer id) {

        if (!teamRepository.existsById(id)) {

            throw new RuntimeException(
                    "Team not found with id: " + id
            );
        }

        teamRepository.deleteById(id);
    }


    // =========================
    // ENTITY → RESPONSE DTO
    // =========================
    private TeamResponseDTO convertToResponseDTO(Team team) {

        TeamResponseDTO response = new TeamResponseDTO();

        response.setTeamId(team.getTeamId());
        response.setTeamName(team.getTeamName());
        response.setShortName(team.getShortName());
        response.setCity(team.getCity());
        response.setCaptain(team.getCaptain());
        response.setHomeGround(team.getHomeGround());

        return response;
    }
}