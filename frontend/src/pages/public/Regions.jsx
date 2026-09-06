import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import regionService from '../../api/regionService';
import LoadingSpinner from '../../components/LoadingSpinner';
import { FiMapPin, FiArrowRight } from 'react-icons/fi';

const Regions = () => {
  const [regions, setRegions] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadRegions();
  }, []);

  const loadRegions = async () => {
    try {
      const data = await regionService.getByLang();
      setRegions(data || []);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <LoadingSpinner size="lg" />;

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 className="page-title">Regionlar</h1>
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {regions.map((region) => (
          <Link
            key={region.id}
            to={`/region/${region.id}`}
            className="card p-6 flex items-center justify-between group"
          >
            <div className="flex items-center space-x-4">
              <div className="w-12 h-12 rounded-xl bg-orange-50 flex items-center justify-center">
                <FiMapPin className="text-orange-600 text-xl" />
              </div>
              <span className="font-semibold text-gray-900 group-hover:text-primary-600 transition-colors">
                {region.name || region.nameUz}
              </span>
            </div>
            <FiArrowRight className="text-gray-400 group-hover:text-primary-600 transition-colors" />
          </Link>
        ))}
      </div>
    </div>
  );
};

export default Regions;
