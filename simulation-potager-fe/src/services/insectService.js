import axios from "axios";
const API_URL = "http://localhost:8080/api/insectes";

export default {
  getAll: () => axios.get(API_URL),
  getById: (id) => axios.get(`${API_URL}/${id}`),
  create: (insect) => axios.post(API_URL, insect),
  delete: (id) => axios.delete(`${API_URL}/${id}`),
};
