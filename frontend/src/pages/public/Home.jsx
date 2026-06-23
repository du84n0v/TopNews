import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import articleService from '../../api/articleService';
import sectionService from '../../api/sectionService';
import ArticleCard from '../../components/ArticleCard';
import LoadingSpinner from '../../components/LoadingSpinner';
import { FiArrowRight } from 'react-icons/fi';

const Home = () => {
  const [latestArticles, setLatestArticles] = useState([]);
  const [sections, setSections] = useState([]);
  const [sectionArticles, setSectionArticles] = useState({});
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, []);

  const loadData = async () => {
    try {
      setLoading(true);
      // Oxirgi 12 ta maqolani olish
      const articlesRes = await articleService.getLast12([], 1, 12);
      setLatestArticles(articlesRes.content || []);

      // Bo'limlarni olish
      const sectionsRes = await sectionService.getByLang();
      setSections(sectionsRes || []);

      // Har bir bo'lim uchun maqolalar
      const sArticles = {};
      for (const section of (sectionsRes || []).slice(0, 4)) {
        try {
          const res = await articleService.getBySection(section.id, 1, 4);
          sArticles[section.id] = res.content || [];
        } catch (e) {
          sArticles[section.id] = [];
        }
      }
      setSectionArticles(sArticles);
    } catch (error) {
      console.error('Error loading data:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <LoadingSpinner size="lg" text="Yuklanmoqda..." />;
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      {/* Hero Section - Featured Articles */}
      {latestArticles.length > 0 && (
        <section className="mb-12">
          <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
            {/* Main featured */}
            <div className="lg:col-span-2">
              <ArticleCard article={latestArticles[0]} variant="featured" />
            </div>
            {/* Side articles */}
            <div className="space-y-4">
              {latestArticles.slice(1, 4).map((article) => (
                <ArticleCard
                  key={article.articleId}
                  article={article}
                  variant="horizontal"
                />
              ))}
            </div>
          </div>
        </section>
      )}

      {/* Latest Articles Grid */}
      {latestArticles.length > 4 && (
        <section className="mb-12">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold text-gray-900">
              So'nggi yangiliklar
            </h2>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {latestArticles.slice(4, 12).map((article) => (
              <ArticleCard key={article.articleId} article={article} />
            ))}
          </div>
        </section>
      )}

      {/* Section Based Articles */}
      {sections.slice(0, 4).map((section) => (
        <section key={section.id} className="mb-12">
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-2xl font-bold text-gray-900">
              {section.name || section.nameUz}
            </h2>
            <Link
              to={`/section/${section.id}`}
              className="flex items-center space-x-1 text-primary-600 hover:text-primary-700 font-medium text-sm"
            >
              <span>Barchasini ko'rish</span>
              <FiArrowRight />
            </Link>
          </div>
          <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
            {(sectionArticles[section.id] || []).map((article) => (
              <ArticleCard key={article.articleId} article={article} />
            ))}
          </div>
          {(!sectionArticles[section.id] ||
            sectionArticles[section.id].length === 0) && (
            <p className="text-gray-400 text-center py-8">
              Hozircha maqola mavjud emas
            </p>
          )}
        </section>
      ))}
    </div>
  );
};

export default Home;
