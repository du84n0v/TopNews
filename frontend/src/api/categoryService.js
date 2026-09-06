import api from './index';

const categoryService = {
  // Kategoriya yaratish (ADMIN)
  create: async (data) => {
    const response = await api.post('/category/create', data);
    return response.data;
  },

  // Kategoriyani yangilash (ADMIN)
  update: async (categoryId, data) => {
    const response = await api.put(`/category/update/by-id/${categoryId}`, data);
    return response.data;
  },

  // Kategoriyani o'chirish (ADMIN)
  delete: async (categoryId) => {
    const response = await api.put(`/category/detele/by-id/${categoryId}`);
    return response.data;
  },

  // Barcha kategoriyalar (ADMIN)
  getAll: async () => {
    const response = await api.get('/category/list');
    return response.data;
  },

  // Til bo'yicha kategoriyalar (PUBLIC)
  getByLang: async () => {
    const response = await api.get('/category/by-lang');
    return response.data;
  },
};

export default categoryService;
