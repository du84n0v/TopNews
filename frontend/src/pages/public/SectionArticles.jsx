import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import articleService from '../../api/articleService';
import sectionService from '../../api/sectionService';
import ArticleCard from '../../components/ArticleCard';
import Pagination from '../../components/Pagination';
import LoadingSpinner from '../../components/LoadingSpinner';

const SectionArticles = () => {
  const { sectionId } = useParams();
  const [articles, setArticles] = useState([]);
  const [section, setSection] = useState(null);
  const [page, setPage] = useState(1);
  const [totalPages, setTotalPages] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, [sectionId, page]);

  const loadData = async () => {
    try {
      setLoading(true);
      const res = await articleService.getBySection(sectionId, page, 12);
      setArticles(res.content || []);
      setTotalPages(res.totalPages || 0);

      // Bo'lim nomini olish
      const sections = await sectionService.getByLang();
      const current = sections.find((s) => s.id === parseInt(sectionId));
      setSection(current);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner size="lg" text="Yuklanmoqda..." />;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      {/* Breadcrumb */}
      <nav className="flex items-center space-x-2 text-sm text-gray-500 mb-6">
        <Link to="/" className="hover:text-primary-600">Bosh sahifa</Link>
        <span>/</span>
        <Link to="/sections" className="hover:text-primary-600">Bo'limlar</Link>
        <span>/</span>
        <span className="text-gray-700">{section?.name || section?.nameUz}</span>
      </nav>

      <h1 className="page-title">
        {section?.name || section?.nameUz || "Bo'lim"}
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

export default SectionArticles;
