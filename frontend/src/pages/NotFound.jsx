import { Link } from 'react-router-dom';
import { MdNewspaper } from 'react-icons/md';
import { FiHome } from 'react-icons/fi';

const NotFound = () => {
  return (
    <div className="min-h-[60vh] flex flex-col items-center justify-center px-4">
      <MdNewspaper className="text-primary-200 text-8xl mb-6" />
      <h1 className="text-6xl font-bold text-gray-900 mb-2">404</h1>
      <p className="text-xl text-gray-500 mb-8">Sahifa topilmadi</p>
      <Link to="/" className="btn-primary flex items-center space-x-2">
        <FiHome />
        <span>Bosh sahifaga qaytish</span>
      </Link>
    </div>
  );
};

export default NotFound;
