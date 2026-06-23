import { useState, useEffect } from 'react';
import commentService from '../../api/commentService';
import Pagination from '../../components/Pagination';
import LoadingSpinner from '../../components/LoadingSpinner';
import { formatDate } from '../../utils/helpers';
import { FiTrash2, FiSearch } from 'react-icons/fi';
import { toast } from 'react-toastify';

const CommentManagement = () => {
  const [comments, setComments] = useState([]);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => { loadData(); }, [page]);

  const loadData = async () => {
    try {
      setLoading(true);
      const data = await commentService.filter({}, page, 10);
      setComments(data.content || []);
      setTotalPages(data.totalPages || 0);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!confirm("O'chirmoqchimisiz?")) return;
    try {
      await commentService.delete(id);
      toast.success("O'chirildi!");
      loadData();
    } catch (error) {
      toast.error("Xatolik");
    }
  };

  if (loading) return <LoadingSpinner />;

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-900">Izohlar</h1>
      </div>

      <div className="card overflow-hidden">
        <div className="overflow-x-auto">
          <table className="w-full">
            <thead className="bg-gray-50">
              <tr>
                <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Izoh</th>
                <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Maqola ID</th>
                <th className="px-4 py-3 text-left text-xs font-semibold text-gray-600 uppercase">Sana</th>
                <th className="px-4 py-3 text-right text-xs font-semibold text-gray-600 uppercase">Amallar</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-100">
              {comments.map((comment) => (
                <tr key={comment.id} className="hover:bg-gray-50">
                  <td className="px-4 py-3">
                    <p className="text-sm text-gray-900 line-clamp-2">{comment.content}</p>
                  </td>
                  <td className="px-4 py-3 text-sm text-gray-500">
                    {comment.articleId}
                  </td>
                  <td className="px-4 py-3 text-xs text-gray-400">
                    {formatDate(comment.createdDate)}
                  </td>
                  <td className="px-4 py-3">
                    <div className="flex justify-end">
                      <button onClick={() => handleDelete(comment.id)} className="p-1.5 text-red-600 hover:bg-red-50 rounded-lg">
                        <FiTrash2 size={16} />
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        {comments.length === 0 && <p className="text-center py-8 text-gray-400">Izoh yo'q</p>}
      </div>

      <Pagination currentPage={page} totalPages={totalPages} onPageChange={setPage} />
    </div>
  );
};

export default CommentManagement;
