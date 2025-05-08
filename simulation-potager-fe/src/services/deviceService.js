import axios from "axios";
const API_URL = "http://localhost:8080/api/dispositifs";

export default {
  getAll: () => axios.get(API_URL),
  getById: (id) => axios.get(`${API_URL}/${id}`),
  create: (device) => axios.post(API_URL, device),
  delete: (id) => axios.delete(`${API_URL}/${id}`),
};
