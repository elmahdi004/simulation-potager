import axios from "axios";
const API_URL = "http://localhost:8080/api/plantes";

export default {
  getAll: () => axios.get(API_URL),
  getById: (id) => axios.get(`${API_URL}/${id}`),
  create: (plant) => axios.post(API_URL, plant),
  delete: (id) => axios.delete(`${API_URL}/${id}`),
};
