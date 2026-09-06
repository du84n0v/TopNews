import api from './index';

const authService = {
  // Ro'yxatdan o'tish
  register: async (data) => {
    const response = await api.post('/auth/registration', data);
    return response.data;
  },

  // Email tasdiqlash
  verify: async (data) => {
    const response = await api.post('/auth/verify', data);
    return response.data;
  },

  // Kodni qayta yuborish
  resendCode: async (email) => {
    const response = await api.post(`/auth/resend-code?email=${email}`);
    return response.data;
  },

  // Kirish
  login: async (data) => {
    const response = await api.post('/auth/login', data);
    return response.data;
  },

  // Profilni yangilash
  updateProfile: async (profileId, data) => {
    const response = await api.put(`/auth/update/${profileId}`, data);
    return response.data;
  },
};

export default authService;
