import api from './index';

const commentService = {
  // Izoh yozish
  create: async (data) => {
    const response = await api.post('/comment/create', data);
    return response.data;
  },

  // Izohni yangilash
  update: async (commentId, data) => {
    const response = await api.put(`/comment/update/${commentId}`, data);
    return response.data;
  },

  // Izohni o'chirish
  delete: async (commentId) => {
    const response = await api.put(`/comment/delete/${commentId}`);
    return response.data;
  },

  // Javoblarni olish
  getReplies: async (commentId) => {
    const response = await api.get(`/comment/replies/${commentId}`);
    return response.data;
  },

  // Maqola izohlari
  getArticleComments: async (articleId) => {
    const response = await api.get(`/comment/article/${articleId}`);
    return response.data;
  },

  // Filter (ADMIN)
  filter: async (filterData, page = 1, size = 5) => {
    const response = await api.post(`/comment/filter?page=${page}&size=${size}`, filterData);
    return response.data;
  },
};

export default commentService;
