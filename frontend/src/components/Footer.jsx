import { Link } from 'react-router-dom';
import { MdNewspaper } from 'react-icons/md';
import { FiMail, FiPhone, FiMapPin } from 'react-icons/fi';
import { FaTelegram, FaInstagram, FaFacebook } from 'react-icons/fa';

const Footer = () => {
  return (
    <footer className="bg-gray-900 text-gray-300">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="grid grid-cols-1 md:grid-cols-4 gap-8">
          {/* Brand */}
          <div className="col-span-1 md:col-span-2">
            <Link to="/" className="flex items-center space-x-2 mb-4">
              <MdNewspaper className="text-primary-400 text-3xl" />
              <span className="text-2xl font-bold text-white">
                Top<span className="text-primary-400">News</span>
              </span>
            </Link>
            <p className="text-gray-400 mb-4 max-w-md">
              O'zbekistonning eng ishonchli yangiliklar portali. Eng so'nggi va dolzarb
              yangiliklar bilan doimo xabardor bo'ling.
            </p>
            <div className="flex space-x-4">
              <a href="#" className="text-gray-400 hover:text-primary-400 transition-colors">
                <FaTelegram size={22} />
              </a>
              <a href="#" className="text-gray-400 hover:text-primary-400 transition-colors">
                <FaInstagram size={22} />
              </a>
              <a href="#" className="text-gray-400 hover:text-primary-400 transition-colors">
                <FaFacebook size={22} />
              </a>
            </div>
          </div>

          {/* Quick Links */}
          <div>
            <h3 className="text-white font-semibold text-lg mb-4">Tezkor havolalar</h3>
            <ul className="space-y-2">
              <li>
                <Link to="/" className="text-gray-400 hover:text-white transition-colors">
                  Bosh sahifa
                </Link>
              </li>
              <li>
                <Link to="/sections" className="text-gray-400 hover:text-white transition-colors">
                  Bo'limlar
                </Link>
              </li>
              <li>
                <Link to="/categories" className="text-gray-400 hover:text-white transition-colors">
                  Kategoriyalar
                </Link>
              </li>
              <li>
                <Link to="/regions" className="text-gray-400 hover:text-white transition-colors">
                  Regionlar
                </Link>
              </li>
            </ul>
          </div>

          {/* Contact */}
          <div>
            <h3 className="text-white font-semibold text-lg mb-4">Aloqa</h3>
            <ul className="space-y-3">
              <li className="flex items-center space-x-2">
                <FiMail className="text-primary-400" />
                <span className="text-gray-400">info@topnews.uz</span>
              </li>
              <li className="flex items-center space-x-2">
                <FiPhone className="text-primary-400" />
                <span className="text-gray-400">+998 90 123 45 67</span>
              </li>
              <li className="flex items-center space-x-2">
                <FiMapPin className="text-primary-400" />
                <span className="text-gray-400">Toshkent, O'zbekiston</span>
              </li>
            </ul>
          </div>
        </div>

        {/* Bottom */}
        <div className="border-t border-gray-800 mt-10 pt-6 text-center">
          <p className="text-gray-500 text-sm">
            &copy; {new Date().getFullYear()} TopNews. Barcha huquqlar himoyalangan.
          </p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
