import axios from "axios";

const API_URL = "/api/players";

const PAGE_SIZE = 20;


// Get players with pagination
export const getAllPlayers = (page = 0) => {
    return axios.get(API_URL, {
        params: {
            page: page,
            size: PAGE_SIZE
        }
    });
};


// Get player by ID
export const getPlayerById = (id) => {
    return axios.get(`${API_URL}/${id}`);
};


// Create player
export const createPlayer = (player) => {
    return axios.post(API_URL, player);
};


// Update player
export const updatePlayer = (id, player) => {
    return axios.put(`${API_URL}/${id}`, player);
};


// Delete player
export const deletePlayer = (id) => {
    return axios.delete(`${API_URL}/${id}`);
};