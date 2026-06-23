import api from './index';

const articleService = {
  // Maqola yaratish (MODERATOR, ADMIN)
  create: async (data) => {
    const response = await api.post('/article/create', data);
    return response.data;
  },

  // Maqolani yangilash (MODERATOR, ADMIN)
  update: async (articleId, data) => {
    const response = await api.put(`/article/update/${articleId}`, data);
    return response.data;
  },

  // Maqolani o'chirish (MODERATOR, ADMIN)
  delete: async (articleId) => {
    const response = await api.put(`/article/delete/${articleId}`);
    return response.data;
  },

  // Maqola statusini o'zgartirish (PUBLISHER, ADMIN)
  changeStatus: async (articleId, data) => {
    const response = await api.put(`/article/change-status/${articleId}`, data);
    return response.data;
  },

  // Maqolani ID bo'yicha olish
  getById: async (articleId) => {
    const response = await api.get(`/article/get-by-id/${articleId}`);
    return response.data;
  },

  // Bo'lim bo'yicha oxirgi maqolalar
  getBySection: async (sectionId, page = 1, size = 5) => {
    const response = await api.get(`/article/last-n-by-section/${sectionId}?page=${page}&size=${size}`);
    return response.data;
  },

  // Kategoriya bo'yicha oxirgi maqolalar
  getByCategory: async (categoryId, page = 1, size = 5) => {
    const response = await api.get(`/article/last-n-by-category/${categoryId}?page=${page}&size=${size}`);
    return response.data;
  },

  // Region bo'yicha oxirgi maqolalar
  getByRegion: async (regionId, page = 1, size = 5) => {
    const response = await api.get(`/article/last-n-by-region/${regionId}?page=${page}&size=${size}`);
    return response.data;
  },

  // Oxirgi 12 ta maqola
  getLast12: async (excludeIds = [], page = 1, size = 12) => {
    const response = await api.post(`/article/last-12?page=${page}&size=${size}`, excludeIds);
    return response.data;
  },

  // Ko'p o'qilganlar (articleId dan tashqari)
  getMostRead: async (articleId, page = 1, size = 5) => {
    const response = await api.get(`/article/most-read-except/${articleId}?page=${page}&size=${size}`);
    return response.data;
  },

  // Ko'rish sonini oshirish
  increaseViewCount: async (articleId) => {
    const response = await api.post(`/article/increase-view-count-by-id/${articleId}`);
    return response.data;
  },

  // Ulashish sonini oshirish
  increaseShareCount: async (articleId) => {
    const response = await api.post(`/article/increase-share-count-by-id/${articleId}`);
    return response.data;
  },

  // Hamma uchun filter
  filter: async (filterData, page = 1, size = 5) => {
    const response = await api.post(`/article/filter?page=${page}&size=${size}`, filterData);
    return response.data;
  },

  // Moderator uchun filter
  filterForModerator: async (filterData, page = 1, size = 5) => {
    const response = await api.post(`/article/moderator/filter?page=${page}&size=${size}`, filterData);
    return response.data;
  },

  // Publisher uchun filter
  filterForPublisher: async (filterData, page = 1, size = 5) => {
    const response = await api.post(`/article/publisher/filter?page=${page}&size=${size}`, filterData);
    return response.data;
  },
};

export default articleService;
