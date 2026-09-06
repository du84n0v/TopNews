import { useState, useEffect } from 'react';
import regionService from '../../api/regionService';
import LoadingSpinner from '../../components/LoadingSpinner';
import { FiPlus, FiEdit2, FiTrash2, FiX } from 'react-icons/fi';
import { toast } from 'react-toastify';

const RegionManagement = () => {
  const [regions, setRegions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editing, setEditing] = useState(null);
  const [formData, setFormData] = useState({
    orderNumber: 1, nameUz: '', nameRu: '', nameEn: '', key: '',
  });

  useEffect(() => { loadData(); }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      const data = await regionService.getAll();
      setRegions(data || []);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = () => {
    setEditing(null);
    setFormData({ orderNumber: 1, nameUz: '', nameRu: '', nameEn: '', key: '' });
    setShowModal(true);
  };

  const handleEdit = (reg) => {
    setEditing(reg);
    setFormData({
      orderNumber: reg.orderNumber || 1,
      nameUz: reg.nameUz || '',
      nameRu: reg.nameRu || '',
      nameEn: reg.nameEn || '',
      key: reg.key || '',
    });
    setShowModal(true);
  };

  const handleDelete = async (id) => {
    if (!confirm("O'chirmoqchimisiz?")) return;
    try {
      await regionService.delete(id);
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
        await regionService.update(editing.id, formData);
        toast.success("Yangilandi!");
      } else {
        await regionService.create(formData);
        toast.success("Yaratildi!");
      }
      setShowModal(false);
      loadData();
    } catch (error) {
      toast.error("Xatolik");
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Regionlar</h1>
        <button onClick={handleCreate} className="btn-primary flex items-center space-x-2">
          <FiPlus /> <span>Yangi</span>
        </button>
      </div>

      <div className="card overflow-hidden">
        <table className="w-full">
          <thead className="bg-gray-50">
            <tr>
              <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">#</th>
              <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Nomi (UZ)</th>
              <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Nomi (RU)</th>
              <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Nomi (EN)</th>
              <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Key</th>
              <th className="px-4 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Amallar</th>
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100">
            {regions.map((reg) => (
              <tr key={reg.id} className="hover:bg-gray-50">
                <td className="px-4 py-3 text-sm">{reg.orderNumber}</td>
                <td className="px-4 py-3 text-sm font-medium">{reg.nameUz}</td>
                <td className="px-4 py-3 text-sm">{reg.nameRu}</td>
                <td className="px-4 py-3 text-sm">{reg.nameEn}</td>
                <td className="px-4 py-3 text-sm text-gray-500">{reg.key}</td>
                <td className="px-4 py-3">
                  <div className="flex items-center justify-end space-x-2">
                    <button onClick={() => handleEdit(reg)} className="p-1.5 text-blue-600 hover:bg-blue-50 rounded-lg">
                      <FiEdit2 size={16} />
                    </button>
                    <button onClick={() => handleDelete(reg.id)} className="p-1.5 text-red-600 hover:bg-red-50 rounded-lg">
                      <FiTrash2 size={16} />
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
        {regions.length === 0 && <p className="text-center py-8 text-gray-400">Ma'lumot yo'q</p>}
      </div>

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl max-w-md w-full p-6">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-bold">{editing ? 'Tahrirlash' : 'Yangi region'}</h2>
              <button onClick={() => setShowModal(false)} className="p-2 hover:bg-gray-100 rounded-lg"><FiX /></button>
            </div>
            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Tartib raqami</label>
                <input type="number" value={formData.orderNumber} onChange={(e) => setFormData({...formData, orderNumber: parseInt(e.target.value)})} className="input-field" required />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Nomi (UZ)</label>
                <input type="text" value={formData.nameUz} onChange={(e) => setFormData({...formData, nameUz: e.target.value})} className="input-field" required />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Nomi (RU)</label>
                <input type="text" value={formData.nameRu} onChange={(e) => setFormData({...formData, nameRu: e.target.value})} className="input-field" required />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Nomi (EN)</label>
                <input type="text" value={formData.nameEn} onChange={(e) => setFormData({...formData, nameEn: e.target.value})} className="input-field" required />
              </div>
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Key</label>
                <input type="text" value={formData.key} onChange={(e) => setFormData({...formData, key: e.target.value})} className="input-field" required />
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

export default RegionManagement;
