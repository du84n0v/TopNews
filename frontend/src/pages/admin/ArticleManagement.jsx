import { useState, useEffect } from 'react';
import { useAuth } from '../../context/AuthContext';
import articleService from '../../api/articleService';
import categoryService from '../../api/categoryService';
import sectionService from '../../api/sectionService';
import regionService from '../../api/regionService';
import attachService from '../../api/attachService';
import Pagination from '../../components/Pagination';
import LoadingSpinner from '../../components/LoadingSpinner';
import { formatDate, getStatusColor, getStatusName } from '../../utils/helpers';
import {
  FiPlus, FiEdit2, FiTrash2, FiSearch, FiX, FiCheck, FiImage
} from 'react-icons/fi';
import { toast } from 'react-toastify';

const ArticleManagement = () => {
  const { isAdmin, isModerator, isPublisher } = useAuth();
  const [articles, setArticles] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);
  const [showModal, setShowModal] = useState(false);
  const [editingArticle, setEditingArticle] = useState(null);
  const [categories, setCategories] = useState([]);
  const [sections, setSections] = useState([]);
  const [regions, setRegions] = useState([]);
  const [searchTitle, setSearchTitle] = useState('');

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    content: '',
    imageId: '',
    regionId: '',
    categoryIdList: [],
    sectionIdList: [],
  });

  useEffect(() => {
    loadData();
  }, [page]);

  useEffect(() => {
    loadMeta();
  }, []);

  const loadMeta = async () => {
    try {
      const [cats, secs, regs] = await Promise.all([
        categoryService.getByLang(),
        sectionService.getByLang(),
        regionService.getByLang(),
      ]);
      setCategories(cats || []);
      setSections(secs || []);
      setRegions(regs || []);
    } catch (error) {
      console.error('Error loading meta:', error);
    }
  };

  const loadData = async () => {
    try {
      setLoading(true);
      let res;
      const filter = searchTitle ? { title: searchTitle } : {};

      if (isModerator() && !isAdmin()) {
        res = await articleService.filterForModerator(filter, page, 10);
      } else if (isPublisher() && !isAdmin()) {
        res = await articleService.filterForPublisher(filter, page, 10);
      } else {
        res = await articleService.filter(filter, page, 10);
      }
      setArticles(res.content || []);
      setTotalPages(res.totalPages || 0);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreate = () => {
    setEditingArticle(null);
    setFormData({
      title: '', description: '', content: '',
      imageId: '', regionId: '', categoryIdList: [], sectionIdList: [],
    });
    setShowModal(true);
  };

  const handleEdit = (article) => {
    setEditingArticle(article);
    setFormData({
      title: article.title || '',
      description: article.description || '',
      content: '',
      imageId: '',
      regionId: '',
      categoryIdList: [],
      sectionIdList: [],
    });
    setShowModal(true);
  };

  const handleDelete = async (articleId) => {
    if (!confirm("Rostdan o'chirmoqchimisiz?")) return;
    try {
      await articleService.delete(articleId);
      toast.success("Maqola o'chirildi!");
      loadData();
    } catch (error) {
      toast.error("Xatolik yuz berdi");
    }
  };

  const handleStatusChange = async (articleId, status) => {
    try {
      await articleService.changeStatus(articleId, { status });
      toast.success("Status o'zgartirildi!");
      loadData();
    } catch (error) {
      toast.error("Xatolik yuz berdi");
    }
  };

  const handleImageUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;
    try {
      const attach = await attachService.upload(file);
      setFormData({ ...formData, imageId: attach.id });
      toast.success("Rasm yuklandi!");
    } catch (error) {
      toast.error("Rasm yuklashda xatolik");
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const data = {
        ...formData,
        regionId: formData.regionId ? parseInt(formData.regionId) : null,
      };
      if (editingArticle) {
        await articleService.update(editingArticle.articleId, data);
        toast.success("Maqola yangilandi!");
      } else {
        await articleService.create(data);
        toast.success("Maqola yaratildi!");
      }
      setShowModal(false);
      loadData();
    } catch (error) {
      toast.error("Xatolik yuz berdi");
    }
  };

  const handleCategoryToggle = (catId) => {
    const list = formData.categoryIdList.includes(catId)
      ? formData.categoryIdList.filter((id) => id !== catId)
      : [...formData.categoryIdList, catId];
    setFormData({ ...formData, categoryIdList: list });
  };

  const handleSectionToggle = (secId) => {
    const list = formData.sectionIdList.includes(secId)
      ? formData.sectionIdList.filter((id) => id !== secId)
      : [...formData.sectionIdList, secId];
    setFormData({ ...formData, sectionIdList: list });
  };

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Maqolalar</h1>
        {(isAdmin() || isModerator()) && (
          <button onClick={handleCreate} className="btn-primary flex items-center space-x-2">
            <FiPlus />
            <span>Yangi maqola</span>
          </button>
        )}
      </div>

      {/* Search */}
      <div className="card p-4 mb-6">
        <div className="flex space-x-4">
          <div className="flex-1 relative">
            <FiSearch className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
            <input
              type="text"
              value={searchTitle}
              onChange={(e) => setSearchTitle(e.target.value)}
              placeholder="Sarlavha bo'yicha qidirish..."
              className="input-field pl-10"
            />
          </div>
          <button onClick={loadData} className="btn-primary">Qidirish</button>
        </div>
      </div>

      {/* Table */}
      {loading ? (
        <LoadingSpinner />
      ) : (
        <div className="card overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Sarlavha</th>
                  <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Sana</th>
                  <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Status</th>
                  <th className="px-4 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Amallar</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-100">
                {articles.map((article) => (
                  <tr key={article.articleId} className="hover:bg-gray-50">
                    <td className="px-4 py-3">
                      <p className="font-medium text-gray-900 line-clamp-1">{article.title}</p>
                      <p className="text-xs text-gray-500 line-clamp-1">{article.description}</p>
                    </td>
                    <td className="px-4 py-3 text-sm text-gray-500">
                      {formatDate(article.publishedDate)}
                    </td>
                    <td className="px-4 py-3">
                      <span className={`px-2 py-1 rounded-full text-xs font-medium ${getStatusColor(article.status)}`}>
                        {getStatusName(article.status)}
                      </span>
                    </td>
                    <td className="px-4 py-3">
                      <div className="flex items-center justify-end space-x-2">
                        {(isAdmin() || isPublisher()) && (
                          <button
                            onClick={() => handleStatusChange(article.articleId, 'PUBLISHED')}
                            className="p-1.5 text-green-600 hover:bg-green-50 rounded-lg"
                            title="Nashr qilish"
                          >
                            <FiCheck size={16} />
                          </button>
                        )}
                        {(isAdmin() || isModerator()) && (
                          <>
                            <button
                              onClick={() => handleEdit(article)}
                              className="p-1.5 text-blue-600 hover:bg-blue-50 rounded-lg"
                              title="Tahrirlash"
                            >
                              <FiEdit2 size={16} />
                            </button>
                            <button
                              onClick={() => handleDelete(article.articleId)}
                              className="p-1.5 text-red-600 hover:bg-red-50 rounded-lg"
                              title="O'chirish"
                            >
                              <FiTrash2 size={16} />
                            </button>
                          </>
                        )}
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
          {articles.length === 0 && (
            <p className="text-center py-8 text-gray-400">Maqola topilmadi</p>
          )}
        </div>
      )}

      <Pagination currentPage={page} totalPages={totalPages} onPageChange={setPage} />

      {/* Modal */}
      {showModal && (
        <div className="fixed inset-0 bg-black/50 flex items-center justify-center z-50 p-4">
          <div className="bg-white rounded-2xl max-w-2xl w-full max-h-[90vh] overflow-y-auto p-6">
            <div className="flex items-center justify-between mb-6">
              <h2 className="text-xl font-bold">
                {editingArticle ? 'Maqolani tahrirlash' : 'Yangi maqola'}
              </h2>
              <button onClick={() => setShowModal(false)} className="p-2 hover:bg-gray-100 rounded-lg">
                <FiX />
              </button>
            </div>

            <form onSubmit={handleSubmit} className="space-y-4">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Sarlavha *</label>
                <input
                  type="text"
                  value={formData.title}
                  onChange={(e) => setFormData({ ...formData, title: e.target.value })}
                  className="input-field"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Qisqa tavsif *</label>
                <textarea
                  value={formData.description}
                  onChange={(e) => setFormData({ ...formData, description: e.target.value })}
                  className="input-field h-20 resize-none"
                  required
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Kontent *</label>
                <textarea
                  value={formData.content}
                  onChange={(e) => setFormData({ ...formData, content: e.target.value })}
                  className="input-field h-40 resize-none"
                  required
                  placeholder="HTML formatda yozing..."
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Rasm</label>
                <div className="flex items-center space-x-3">
                  <label className="btn-secondary flex items-center space-x-2 cursor-pointer">
                    <FiImage />
                    <span>Rasm yuklash</span>
                    <input type="file" accept="image/*" onChange={handleImageUpload} className="hidden" />
                  </label>
                  {formData.imageId && (
                    <span className="text-sm text-green-600">Yuklangan</span>
                  )}
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">Region</label>
                <select
                  value={formData.regionId}
                  onChange={(e) => setFormData({ ...formData, regionId: e.target.value })}
                  className="input-field"
                >
                  <option value="">Tanlang...</option>
                  {regions.map((r) => (
                    <option key={r.id} value={r.id}>{r.name || r.nameUz}</option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Kategoriyalar *</label>
                <div className="flex flex-wrap gap-2">
                  {categories.map((cat) => (
                    <button
                      key={cat.id}
                      type="button"
                      onClick={() => handleCategoryToggle(cat.id)}
                      className={`px-3 py-1 rounded-full text-sm font-medium transition-colors ${
                        formData.categoryIdList.includes(cat.id)
                          ? 'bg-primary-600 text-white'
                          : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                      }`}
                    >
                      {cat.name || cat.nameUz}
                    </button>
                  ))}
                </div>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-2">Bo'limlar *</label>
                <div className="flex flex-wrap gap-2">
                  {sections.map((sec) => (
                    <button
                      key={sec.id}
                      type="button"
                      onClick={() => handleSectionToggle(sec.id)}
                      className={`px-3 py-1 rounded-full text-sm font-medium transition-colors ${
                        formData.sectionIdList.includes(sec.id)
                          ? 'bg-purple-600 text-white'
                          : 'bg-gray-100 text-gray-700 hover:bg-gray-200'
                      }`}
                    >
                      {sec.name || sec.nameUz}
                    </button>
                  ))}
                </div>
              </div>

              <div className="flex justify-end space-x-3 pt-4 border-t">
                <button type="button" onClick={() => setShowModal(false)} className="btn-secondary">
                  Bekor qilish
                </button>
                <button type="submit" className="btn-primary">
                  {editingArticle ? 'Yangilash' : 'Yaratish'}
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default ArticleManagement;
