import axios from "axios";
const API_URL = "http://localhost:8080/api/parcelles";

export default {
  getAll: () => axios.get(API_URL),
  getById: (id) => axios.get(`${API_URL}/${id}`),
  create: (plot) => axios.post(API_URL, plot),
  delete: (id) => axios.delete(`${API_URL}/${id}`),
};
