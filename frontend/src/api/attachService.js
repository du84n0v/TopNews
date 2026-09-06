import api from './index';

const attachService = {
  // Fayl yuklash
  upload: async (file) => {
    const formData = new FormData();
    formData.append('file', file);
    const response = await api.post('/attaches/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    return response.data;
  },

  // Faylni ochish (URL qaytaradi)
  getOpenUrl: (fileId) => {
    return `/api/v1/attaches/open/${fileId}`;
  },

  // Faylni yuklab olish
  getDownloadUrl: (fileId) => {
    return `/api/v1/attaches/download/${fileId}`;
  },

  // Paginatsiya (ADMIN)
  getPagination: async (page = 1, size = 10) => {
    const response = await api.get(`/attaches/pagination?page=${page}&size=${size}`);
    return response.data;
  },

  // Faylni o'chirish (ADMIN)
  delete: async (fileId) => {
    const response = await api.delete(`/attaches/delete/${fileId}`);
    return response.data;
  },
};

export default attachService;
