import axios from "axios";

const API_URL = "http://localhost:8080/api/players";

export const getAllPlayers = () => {
	return axios.get(API_URL);
};


export const getPlayerById = (id) => {
	return axios.get(`${API_URL}/${id}`);
};


export const createPlayer = (player) => {
	return axios.post(API_URL, player);
}


export const updatePlayer = (id, player) => {
	return axios.put(`${API_URL}/${id}`, player);
};

export const deletePlayer = (id) => {
	return axios.delete(`${API_URL}/${id}`);
};