import axios from "axios";

const API_URL = "/api/teams";

// Get all teams
export const getAllTeams = () => {
    return axios.get(API_URL);
};

// Get team by ID
export const getTeamById = (id) => {
    return axios.get(`${API_URL}/${id}`);
};

// Create team
export const createTeam = (team) => {
    return axios.post(API_URL, team);
};

// Update team
export const updateTeam = (id, team) => {
    return axios.put(`${API_URL}/${id}`, team);
};

// Delete team
export const deleteTeam = (id) => {
    return axios.delete(`${API_URL}/${id}`);
};

// Get players belonging to a team
export const getPlayersByTeam = (teamId) => {
    return axios.get(`${API_URL}/${teamId}/players`);
};