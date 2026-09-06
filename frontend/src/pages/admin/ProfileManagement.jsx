import { useState, useEffect } from 'react';
import profileService from '../../api/profileService';
import Pagination from '../../components/Pagination';
import LoadingSpinner from '../../components/LoadingSpinner';
import { formatDate, getRoleName } from '../../utils/helpers';
import { FiPlus, FiEdit2, FiTrash2, FiX, FiSearch } from 'react-icons/fi';
import { toast } from 'react-toastify';

const ROLES = ['ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR', 'ROLE_PUBLISHER'];
const STATUSES = ['ACTIVE', 'BLOCKED'];

const ProfileManagement = () => {
  const [profiles, setProfiles] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formData, setFormData] = useState({
    name: '', surname: '', username: '', password: '',
    roleList: ['ROLE_USER'], status: 'ACTIVE',
  });

  useEffect(() => { loadData(); }, [page]);

  const loadData = async () => {
    try {
      setLoading(true);
      const data = await profileService.getList(page, 10);
      setProfiles(data.content || []);
      setTotalPages(data.totalPages || 0);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = () => {
    setEditing(null);
    setFormData({
      name: '', surname: '', username: '', password: '',
      roleList: ['ROLE_USER'], status: 'ACTIVE',
    });
    setShowModal(true);
  };

  const handleEdit = (profile) => {
    setEditing(profile);
    setFormData({
      name: profile.name || '',
      surname: profile.surname || '',
      username: profile.username || '',
      password: '',
      roleList: profile.roleList || ['ROLE_USER'],
      status: profile.status || 'ACTIVE',
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (!confirm("O'chirmoqchimisiz?")) return;
    try {
      await profileService.delete(id);
      toast.success("O'chirildi!");
      loadData();
    } catch (error) {
      toast.error("Xatolik");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editing) {
        const data = { ...formData };
        if (!data.password) delete data.password;
        await profileService.update(editing.id, data);
        toast.success("Yangilandi!");
      } else {
        await profileService.create(formData);
        toast.success("Yaratildi!");
      }
      setShowModal(false);
      loadData();
    } catch (error) {
      toast.error("Xatolik");
    }
  };

  const handleRoleToggle = (role) => {
    const list = formData.roleList.includes(role)
      ? formData.roleList.filter((r) => r !== role)
      : [...formData.roleList, role];
    setFormData({ ...formData, roleList: list });
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Foydalanuvchilar</h1>
        <button onClick={handleCreate} className="btn-primary flex items-center space-x-2">
          <FiPlus /> <span>Yangi</span>
        </button>
      </div>

      <div className="card overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Ism</th>
                <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Email</th>
                <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Rollar</th>
                <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Status</th>
                <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Sana</th>
                <th className="px-4 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Amallar</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100">
              {profiles.map((profile) => (
                <tr key={profile.id} className="hover:bg-gray-50">
                  <td className="px-4 py-3">
                    <span className="font-medium text-sm">{profile.name} {profile.surname}</span>
                  </td>
                  <td className="px-4 py-3 text-sm text-gray-500">{profile.username}</td>
                  <td className="px-4 py-3">
                    <div className="flex flex-wrap gap-1">
                      {profile.roleList?.map((role) => (
                        <span key={role} className="px-2 py-0.5 bg-primary-50 text-primary-700 rounded text-xs font-medium">
                          {getRoleName(role)}
                        </span>
                      ))}
                    </div>
                  </td>
                  <td className="px-4 py-3">
                    <span className={`px-2 py-1 rounded-full text-xs font-medium ${
                      profile.status === 'ACTIVE' ? 'bg-green-100 text-green-700' : 'bg-red-100 text-red-700'
                    }`}>
                      {profile.status === 'ACTIVE' ? 'Faol' : 'Bloklangan'}
                    </span>
                  </td>
                  <td className="px-4 py-3 text-xs text-gray-400">{formatDate(profile.createdDate)}</td>
                  <td className="px-4 py-3">
                    <div className="flex items-center justify-end space-x-2">
                      <button onClick={() => handleEdit(profile)} className="p-1.5 text-blue-600 hover:bg-blue-50 rounded-lg">
                        <FiEdit2 size={16} />
                      </button>
                      <button onClick={() => handleDelete(profile.id)} className="p-1.5 text-red-600 hover:bg-red-50 rounded-lg">
                        <FiTrash2 size={16} />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        {profiles.length === 0 && <p className="text-center py-8 text-gray-400">Ma'lumot yo'q</p>}
      </div>

      <Pagination currentPage={page} totalPages={totalPages} onPageChange={setPage} />

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl max-w-md w-full max-h-[90vh] overflow-y-auto p-6">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-bold">{editing ? 'Tahrirlash' : 'Yangi foydalanuvchi'}</h2>
              <button onClick={() => setShowModal(false)} className="p-2 hover:bg-gray-100 rounded-lg"><FiX /></button>
            </div>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Ism</label>
                  <input type="text" value={formData.name} onChange={(e) => setFormData({...formData, name: e.target.value})} className="input-field" required />
                </div>
                <div>
                  <label className="block text-sm font-medium text-gray-700 mb-1">Familiya</label>
                  <input type="text" value={formData.surname} onChange={(e) => setFormData({...formData, surname: e.target.value})} className="input-field" required />
                </div>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
                <input type="email" value={formData.username} onChange={(e) => setFormData({...formData, username: e.target.value})} className="input-field" required />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Parol {editing && <span className="text-gray-400">(bo'sh qoldiring agar o'zgartirmaysiz)</span>}
                </label>
                <input type="password" value={formData.password} onChange={(e) => setFormData({...formData, password: e.target.value})} className="input-field" {...(!editing && { required: true })} minLength={5} maxLength={16} />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Rollar</label>
                <div className="flex flex-wrap gap-2">
                  {ROLES.map((role) => (
                    <button key={role} type="button" onClick={() => handleRoleToggle(role)}
                      className={`px-3 py-1 rounded-full text-sm font-medium transition-colors ${
                        formData.roleList.includes(role) ? 'bg-primary-600 text-white' : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                      }`}>
                      {getRoleName(role)}
                    </button>
                  ))}
                </div>
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Status</label>
                <select value={formData.status} onChange={(e) => setFormData({...formData, status: e.target.value})} className="input-field">
                  {STATUSES.map((s) => (
                    <option key={s} value={s}>{s === 'ACTIVE' ? 'Faol' : 'Bloklangan'}</option>
                  ))}
                </select>
              </div>
              <div className="flex justify-end space-x-3 pt-4 border-t">
                <button type="button" onClick={() => setShowModal(false)} className="btn-secondary">Bekor qilish</button>
                <button type="submit" className="btn-primary">{editing ? 'Yangilash' : 'Yaratish'}</button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProfileManagement;
