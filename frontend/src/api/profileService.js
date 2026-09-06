import api from './index';

const profileService = {
  // Profil yaratish (ADMIN)
  create: async (data) => {
    const response = await api.post('/profile/admin/create', data);
    return response.data;
  },

  // Profilni yangilash (ADMIN)
  update: async (profileId, data) => {
    const response = await api.put(`/profile/admin/update/${profileId}`, data);
    return response.data;
  },

  // Profil detaillarini yangilash
  updateDetail: async (profileId, data) => {
    const response = await api.put(`/profile/update/detail/${profileId}`, data);
    return response.data;
  },

  // ID bo'yicha profil (ADMIN)
  getById: async (profileId) => {
    const response = await api.get(`/profile/by-id/${profileId}`);
    return response.data;
  },

  // Profilni o'chirish (ADMIN)
  delete: async (profileId) => {
    const response = await api.put(`/profile/admin/delete/${profileId}`);
    return response.data;
  },

  // Profillar ro'yxati (ADMIN)
  getList: async (page = 1, size = 10) => {
    const response = await api.get(`/profile/list?page=${page}&size=${size}`);
    return response.data;
  },

  // Profil rasmini yangilash
  updatePhoto: async (attachId) => {
    const response = await api.put(`/profile/update/photo/${attachId}`);
    return response.data;
  },

  // Parolni yangilash
  updatePassword: async (profileId, data) => {
    const response = await api.put(`/profile/update/password/${profileId}`, data);
    return response.data;
  },

  // Filter (ADMIN)
  filter: async (filterData, page = 1, size = 10) => {
    const response = await api.post(`/profile/filter?page=${page}&size=${size}`, filterData);
    return response.data;
  },
};

export default profileService;
