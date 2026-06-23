import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import profileService from '../../api/profileService';
import { FiUser, FiLock, FiSave } from 'react-icons/fi';
import { toast } from 'react-toastify';

const Settings = () => {
  const { user, setUser } = useAuth();
  const [activeTab, setActiveTab] = useState('details');

  // Detail update form
  const [detailForm, setDetailForm] = useState({
    name: user?.name || '',
    surname: user?.surname || '',
  });

  // Password form
  const [passwordForm, setPasswordForm] = useState({
    currentPassword: '',
    newPassword: '',
    confirmPassword: '',
  });

  const [saving, setSaving] = useState(false);

  const handleDetailSubmit = async (e) => {
    e.preventDefault();
    setSaving(true);
    try {
      await profileService.updateDetail(user.id, detailForm);
      const updatedUser = { ...user, ...detailForm };
      setUser(updatedUser);
      localStorage.setItem('user', JSON.stringify(updatedUser));
      toast.success("Ma'lumotlar yangilandi!");
    } catch (error) {
      toast.error("Yangilashda xatolik");
    } finally {
      setSaving(false);
    }
  };

  const handlePasswordSubmit = async (e) => {
    e.preventDefault();
    if (passwordForm.newPassword !== passwordForm.confirmPassword) {
      toast.error("Parollar mos kelmaydi!");
      return;
    }
    setSaving(true);
    try {
      await profileService.updatePassword(user.id, {
        currentPassword: passwordForm.currentPassword,
        newPassword: passwordForm.newPassword,
      });
      setPasswordForm({ currentPassword: '', newPassword: '', confirmPassword: '' });
      toast.success("Parol yangilandi!");
    } catch (error) {
      toast.error("Parolni yangilashda xatolik");
    } finally {
      setSaving(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 className="page-title">Sozlamalar</h1>

      {/* Tabs */}
      <div className="flex space-x-1 bg-gray-100 rounded-xl p-1 mb-8 max-w-md">
        <button
          onClick={() => setActiveTab('details')}
          className={`flex-1 flex items-center justify-center space-x-2 px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
            activeTab === 'details' ? 'bg-white shadow-sm text-primary-600' : 'text-gray-600'
          }`}
        >
          <FiUser />
          <span>Ma'lumotlar</span>
        </button>
        <button
          onClick={() => setActiveTab('password')}
          className={`flex-1 flex items-center justify-center space-x-2 px-4 py-2 rounded-lg text-sm font-medium transition-colors ${
            activeTab === 'password' ? 'bg-white shadow-sm text-primary-600' : 'text-gray-600'
          }`}
        >
          <FiLock />
          <span>Parol</span>
        </button>
      </div>

      {/* Detail Tab */}
      {activeTab === 'details' && (
        <div className="card p-8">
          <h2 className="text-xl font-semibold text-gray-900 mb-6">Shaxsiy ma'lumotlar</h2>
          <form onSubmit={handleDetailSubmit} className="space-y-5 max-w-lg">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Ism</label>
              <input
                type="text"
                value={detailForm.name}
                onChange={(e) => setDetailForm({ ...detailForm, name: e.target.value })}
                className="input-field"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Familiya</label>
              <input
                type="text"
                value={detailForm.surname}
                onChange={(e) => setDetailForm({ ...detailForm, surname: e.target.value })}
                className="input-field"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input
                type="email"
                value={user?.username || ''}
                className="input-field bg-gray-50"
                disabled
              />
              <p className="text-xs text-gray-400 mt-1">Emailni o'zgartirish mumkin emas</p>
            </div>
            <button type="submit" disabled={saving} className="btn-primary flex items-center space-x-2">
              <FiSave />
              <span>{saving ? 'Saqlanmoqda...' : 'Saqlash'}</span>
            </button>
          </form>
        </div>
      )}

      {/* Password Tab */}
      {activeTab === 'password' && (
        <div className="card p-8">
          <h2 className="text-xl font-semibold text-gray-900 mb-6">Parolni o'zgartirish</h2>
          <form onSubmit={handlePasswordSubmit} className="space-y-5 max-w-lg">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Joriy parol</label>
              <input
                type="password"
                value={passwordForm.currentPassword}
                onChange={(e) => setPasswordForm({ ...passwordForm, currentPassword: e.target.value })}
                className="input-field"
                required
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Yangi parol</label>
              <input
                type="password"
                value={passwordForm.newPassword}
                onChange={(e) => setPasswordForm({ ...passwordForm, newPassword: e.target.value })}
                className="input-field"
                required
                minLength={5}
                maxLength={15}
              />
            </div>
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Parolni tasdiqlang</label>
              <input
                type="password"
                value={passwordForm.confirmPassword}
                onChange={(e) => setPasswordForm({ ...passwordForm, confirmPassword: e.target.value })}
                className="input-field"
                required
              />
            </div>
            <button type="submit" disabled={saving} className="btn-primary flex items-center space-x-2">
              <FiSave />
              <span>{saving ? 'Saqlanmoqda...' : "Parolni o'zgartirish"}</span>
            </button>
          </form>
        </div>
      )}
    </div>
  );
};

export default Settings;
