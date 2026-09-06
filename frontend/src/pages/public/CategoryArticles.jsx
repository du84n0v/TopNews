import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import articleService from '../../api/articleService';
import categoryService from '../../api/categoryService';
import ArticleCard from '../../components/ArticleCard';
import Pagination from '../../components/Pagination';
import LoadingSpinner from '../../components/LoadingSpinner';

const CategoryArticles = () => {
  const { categoryId } = useParams();
  const [articles, setArticles] = useState([]);
  const [category, setCategory] = useState(null);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, [categoryId, page]);

  const loadData = async () => {
    try {
      setLoading(true);
      const res = await articleService.getByCategory(categoryId, page, 12);
      setArticles(res.content || []);
      setTotalPages(res.totalPages || 0);

      const categories = await categoryService.getByLang();
      const current = categories.find((c) => c.id === parseInt(categoryId));
      setCategory(current);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner size="lg" text="Yuklanmoqda..." />;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <nav className="flex items-center space-x-2 text-sm text-gray-500 mb-6">
        <Link to="/" className="hover:text-primary-600">Bosh sahifa</Link>
        <span>/</span>
        <Link to="/categories" className="hover:text-primary-600">Kategoriyalar</Link>
        <span>/</span>
        <span className="text-gray-700">{category?.name || category?.nameUz}</span>
      </nav>

      <h1 className="page-title">
        {category?.name || category?.nameUz || 'Kategoriya'}
      </h1>

      {articles.length > 0 ? (
        <>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-6">
            {articles.map((article) => (
              <ArticleCard key={article.articleId} article={article} />
            ))}
          </div>
          <Pagination
            currentPage={page}
            totalPages={totalPages}
            onPageChange={setPage}
          />
        </>
      ) : (
        <div className="text-center py-16">
          <p className="text-gray-400 text-lg">Hozircha maqola mavjud emas</p>
        </div>
      )}
    </div>
  );
};

export default CategoryArticles;
