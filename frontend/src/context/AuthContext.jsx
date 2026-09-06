import { createContext, useContext, useState, useEffect } from 'react';
import authService from '../api/authService';
import { toast } from 'react-toastify';

const AuthContext = createContext(null);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    // LocalStorage dan foydalanuvchini olish
    const savedUser = localStorage.getItem('user');
    const token = localStorage.getItem('token');
    if (savedUser && token) {
      setUser(JSON.parse(savedUser));
    }
    setLoading(false);
  }, []);

  const login = async (credentials) => {
    try {
      const data = await authService.login(credentials);
      const { jwt, ...userData } = data;
      localStorage.setItem('token', jwt);
      localStorage.setItem('user', JSON.stringify(userData));
      setUser(userData);
      toast.success("Muvaffaqiyatli kirdingiz!");
      return { success: true };
    } catch (error) {
      const message = error.response?.data?.message || "Login xatoligi";
      toast.error(message);
      return { success: false, message };
    }
  };

  const register = async (data) => {
    try {
      const result = await authService.register(data);
      toast.success("Ro'yxatdan o'tdingiz! Emailingizni tekshiring.");
      return { success: true, message: result };
    } catch (error) {
      const message = error.response?.data?.message || "Ro'yxatdan o'tishda xatolik";
      toast.error(message);
      return { success: false, message };
    }
  };

  const verify = async (data) => {
    try {
      const result = await authService.verify(data);
      toast.success("Email tasdiqlandi!");
      return { success: true, message: result };
    } catch (error) {
      const message = error.response?.data?.message || "Tasdiqlash xatoligi";
      toast.error(message);
      return { success: false, message };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setUser(null);
    toast.info("Tizimdan chiqdingiz");
  };

  const isAdmin = () => {
    return user?.roleList?.includes('ROLE_ADMIN');
  };

  const isModerator = () => {
    return user?.roleList?.includes('ROLE_MODERATOR');
  };

  const isPublisher = () => {
    return user?.roleList?.includes('ROLE_PUBLISHER');
  };

  const hasRole = (role) => {
    return user?.roleList?.includes(role);
  };

  const value = {
    user,
    setUser,
    login,
    register,
    verify,
    logout,
    loading,
    isAdmin,
    isModerator,
    isPublisher,
    hasRole,
    isAuthenticated: !!user,
  };

  return (
    <AuthContext.Provider value={value}>
      {!loading && children}
    </AuthContext.Provider>
  );
};
