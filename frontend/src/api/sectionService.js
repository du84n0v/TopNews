import api from './index';

const sectionService = {
  // Bo'lim yaratish (ADMIN)
  create: async (data) => {
    const response = await api.post('/section/create', data);
    return response.data;
  },

  // Bo'limni yangilash (ADMIN)
  update: async (sectionId, data) => {
    const response = await api.put(`/section/update/by-id/${sectionId}`, data);
    return response.data;
  },

  // Bo'limni o'chirish (ADMIN)
  delete: async (sectionId) => {
    const response = await api.put(`/section/detele/by-id/${sectionId}`);
    return response.data;
  },

  // Barcha bo'limlar (ADMIN, pagination)
  getAll: async (page = 1, size = 5) => {
    const response = await api.get(`/section/list?page=${page}&size=${size}`);
    return response.data;
  },

  // Til bo'yicha bo'limlar (PUBLIC)
  getByLang: async () => {
    const response = await api.get('/section/by-lang');
    return response.data;
  },
};

export default sectionService;
