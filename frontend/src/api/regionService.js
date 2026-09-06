import api from './index';

const regionService = {
  // Region yaratish (ADMIN)
  create: async (data) => {
    const response = await api.post('/region/create', data);
    return response.data;
  },

  // Regionni yangilash (ADMIN)
  update: async (regionId, data) => {
    const response = await api.put(`/region/update/by-id/${regionId}`, data);
    return response.data;
  },

  // Regionni o'chirish (ADMIN)
  delete: async (regionId) => {
    const response = await api.put(`/region/delete/by-id/${regionId}`);
    return response.data;
  },

  // Barcha regionlar (ADMIN)
  getAll: async () => {
    const response = await api.get('/region/list');
    return response.data;
  },

  // Til bo'yicha regionlar (PUBLIC)
  getByLang: async () => {
    const response = await api.get('/region/by-lang');
    return response.data;
  },
};

export default regionService;
