import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import sectionService from '../../api/sectionService';
import LoadingSpinner from '../../components/LoadingSpinner';
import { FiLayers, FiArrowRight } from 'react-icons/fi';

const Sections = () => {
  const [sections, setSections] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadSections();
  }, []);

  const loadSections = async () => {
    try {
      const data = await sectionService.getByLang();
      setSections(data || []);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner size="lg" />;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 className="page-title">Bo'limlar</h1>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {sections.map((section) => (
          <Link
            key={section.id}
            to={`/section/${section.id}`}
            className="card p-6 flex items-center justify-between group"
          >
            <div className="flex items-center space-x-4">
              <div className="w-12 h-12 rounded-xl bg-primary-50 flex items-center justify-center">
                <FiLayers className="text-primary-600 text-xl" />
              </div>
              <span className="font-semibold text-gray-900 group-hover:text-primary-600 transition-colors">
                {section.name || section.nameUz}
              </span>
            </div>
            <FiArrowRight className="text-gray-400 group-hover:text-primary-600 transition-colors" />
          </Link>
        ))}
      </div>
    </div>
  );
};

export default Sections;
