import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { FiMenu, FiX, FiUser, FiLogOut, FiSettings, FiGrid } from 'react-icons/fi';
import { MdNewspaper } from 'react-icons/md';

const Navbar = () => {
  const { user, isAuthenticated, logout, isAdmin, isModerator, isPublisher } = useAuth();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);
  const [profileMenuOpen, setProfileMenuOpen] = useState(false);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
    setProfileMenuOpen(false);
  };

  const languages = [
    { code: 'UZ', label: "O'z" },
    { code: 'RU', label: 'Ру' },
    { code: 'EN', label: 'En' },
  ];

  const currentLang = localStorage.getItem('language') || 'UZ';

  const changeLang = (code) => {
    localStorage.setItem('language', code);
    window.location.reload();
  };

  return (
    <nav className="bg-white shadow-sm border-b border-gray-100 sticky top-0 z-50">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex justify-between items-center h-16">
          {/* Logo */}
          <Link to="/" className="flex items-center space-x-2">
            <MdNewspaper className="text-primary-600 text-3xl" />
            <span className="text-2xl font-bold text-gray-900">
              Top<span className="text-primary-600">News</span>
            </span>
          </Link>

          {/* Desktop Navigation */}
          <div className="hidden md:flex items-center space-x-6">
            <Link to="/" className="text-gray-700 hover:text-primary-600 font-medium transition-colors">
              Bosh sahifa
            </Link>
            <Link to="/sections" className="text-gray-700 hover:text-primary-600 font-medium transition-colors">
              Bo'limlar
            </Link>
            <Link to="/categories" className="text-gray-700 hover:text-primary-600 font-medium transition-colors">
              Kategoriyalar
            </Link>
            <Link to="/regions" className="text-gray-700 hover:text-primary-600 font-medium transition-colors">
              Regionlar
            </Link>
          </div>

          {/* Right side */}
          <div className="hidden md:flex items-center space-x-4">
            {/* Language Switcher */}
            <div className="flex items-center space-x-1 bg-gray-100 rounded-lg p-1">
              {languages.map((lang) => (
                <button
                  key={lang.code}
                  onClick={() => changeLang(lang.code)}
                  className={`px-2 py-1 rounded text-sm font-medium transition-colors ${
                    currentLang === lang.code
                      ? 'bg-primary-600 text-white'
                      : 'text-gray-600 hover:text-primary-600'
                  }`}
                >
                  {lang.label}
                </button>
              ))}
            </div>

            {/* Auth Buttons */}
            {isAuthenticated ? (
              <div className="relative">
                <button
                  onClick={() => setProfileMenuOpen(!profileMenuOpen)}
                  className="flex items-center space-x-2 bg-gray-100 rounded-full px-3 py-2 hover:bg-gray-200 transition-colors"
                >
                  <FiUser className="text-gray-600" />
                  <span className="text-sm font-medium text-gray-700">{user?.name}</span>
                </button>

                {/* Profile Dropdown */}
                {profileMenuOpen && (
                  <div className="absolute right-0 mt-2 w-56 bg-white rounded-xl shadow-lg border border-gray-100 py-2 z-50">
                    <div className="px-4 py-2 border-b border-gray-100">
                      <p className="text-sm font-semibold text-gray-900">{user?.name} {user?.surname}</p>
                      <p className="text-xs text-gray-500">{user?.username}</p>
                    </div>

                    <Link
                      to="/profile"
                      onClick={() => setProfileMenuOpen(false)}
                      className="flex items-center space-x-2 px-4 py-2 text-sm text-gray-700 hover:bg-gray-50"
                    >
                      <FiUser className="text-gray-400" />
                      <span>Profilim</span>
                    </Link>

                    <Link
                      to="/settings"
                      onClick={() => setProfileMenuOpen(false)}
                      className="flex items-center space-x-2 px-4 py-2 text-sm text-gray-700 hover:bg-gray-50"
                    >
                      <FiSettings className="text-gray-400" />
                      <span>Sozlamalar</span>
                    </Link>

                    {(isAdmin() || isModerator() || isPublisher()) && (
                      <Link
                        to="/admin"
                        onClick={() => setProfileMenuOpen(false)}
                        className="flex items-center space-x-2 px-4 py-2 text-sm text-gray-700 hover:bg-gray-50"
                      >
                        <FiGrid className="text-gray-400" />
                        <span>Boshqaruv paneli</span>
                      </Link>
                    )}

                    <div className="border-t border-gray-100 mt-1">
                      <button
                        onClick={handleLogout}
                        className="flex items-center space-x-2 px-4 py-2 text-sm text-red-600 hover:bg-red-50 w-full"
                      >
                        <FiLogOut className="text-red-400" />
                        <span>Chiqish</span>
                      </button>
                    </div>
                  </div>
                )}
              </div>
            ) : (
              <div className="flex items-center space-x-3">
                <Link to="/login" className="text-gray-700 hover:text-primary-600 font-medium text-sm">
                  Kirish
                </Link>
                <Link to="/register" className="btn-primary text-sm !px-4 !py-1.5">
                  Ro'yxatdan o'tish
                </Link>
              </div>
            )}
          </div>

          {/* Mobile menu button */}
          <button
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
            className="md:hidden p-2 text-gray-600 hover:text-primary-600"
          >
            {mobileMenuOpen ? <FiX size={24} /> : <FiMenu size={24} />}
          </button>
        </div>
      </div>

      {/* Mobile Menu */}
      {mobileMenuOpen && (
        <div className="md:hidden bg-white border-t border-gray-100 pb-4">
          <div className="px-4 py-3 space-y-2">
            <Link
              to="/"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-lg text-gray-700 hover:bg-gray-50 font-medium"
            >
              Bosh sahifa
            </Link>
            <Link
              to="/sections"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-lg text-gray-700 hover:bg-gray-50 font-medium"
            >
              Bo'limlar
            </Link>
            <Link
              to="/categories"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-lg text-gray-700 hover:bg-gray-50 font-medium"
            >
              Kategoriyalar
            </Link>
            <Link
              to="/regions"
              onClick={() => setMobileMenuOpen(false)}
              className="block px-3 py-2 rounded-lg text-gray-700 hover:bg-gray-50 font-medium"
            >
              Regionlar
            </Link>

            {/* Language */}
            <div className="flex items-center space-x-1 px-3 py-2">
              {languages.map((lang) => (
                <button
                  key={lang.code}
                  onClick={() => changeLang(lang.code)}
                  className={`px-3 py-1 rounded text-sm font-medium ${
                    currentLang === lang.code
                      ? 'bg-primary-600 text-white'
                      : 'bg-gray-100 text-gray-600'
                  }`}
                >
                  {lang.label}
                </button>
              ))}
            </div>

            {/* Auth */}
            <div className="border-t border-gray-100 pt-3">
              {isAuthenticated ? (
                <>
                  <Link
                    to="/profile"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-gray-700 hover:bg-gray-50"
                  >
                    Profilim
                  </Link>
                  {(isAdmin() || isModerator() || isPublisher()) && (
                    <Link
                      to="/admin"
                      onClick={() => setMobileMenuOpen(false)}
                      className="block px-3 py-2 rounded-lg text-gray-700 hover:bg-gray-50"
                    >
                      Boshqaruv paneli
                    </Link>
                  )}
                  <button
                    onClick={() => { handleLogout(); setMobileMenuOpen(false); }}
                    className="block w-full text-left px-3 py-2 rounded-lg text-red-600 hover:bg-red-50"
                  >
                    Chiqish
                  </button>
                </>
              ) : (
                <div className="space-y-2">
                  <Link
                    to="/login"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg text-gray-700 hover:bg-gray-50 font-medium"
                  >
                    Kirish
                  </Link>
                  <Link
                    to="/register"
                    onClick={() => setMobileMenuOpen(false)}
                    className="block px-3 py-2 rounded-lg bg-primary-600 text-white text-center font-medium"
                  >
                    Ro'yxatdan o'tish
                  </Link>
                </div>
              )}
            </div>
          </div>
        </div>
      )}
    </nav>
  );
};

export default Navbar;
