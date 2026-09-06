import { NavLink } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import {
  FiGrid,
  FiFileText,
  FiUsers,
  FiFolder,
  FiLayers,
  FiMapPin,
  FiTag,
  FiMessageSquare,
  FiImage,
} from 'react-icons/fi';

const Sidebar = () => {
  const { isAdmin, isModerator, isPublisher } = useAuth();

  const menuItems = [
    {
      label: 'Dashboard',
      path: '/admin',
      icon: <FiGrid />,
      roles: ['admin', 'moderator', 'publisher'],
    },
    {
      label: 'Maqolalar',
      path: '/admin/articles',
      icon: <FiFileText />,
      roles: ['admin', 'moderator', 'publisher'],
    },
    {
      label: 'Kategoriyalar',
      path: '/admin/categories',
      icon: <FiFolder />,
      roles: ['admin'],
    },
    {
      label: "Bo'limlar",
      path: '/admin/sections',
      icon: <FiLayers />,
      roles: ['admin'],
    },
    {
      label: 'Regionlar',
      path: '/admin/regions',
      icon: <FiMapPin />,
      roles: ['admin'],
    },
    {
      label: 'Teglar',
      path: '/admin/tags',
      icon: <FiTag />,
      roles: ['admin'],
    },
    {
      label: 'Izohlar',
      path: '/admin/comments',
      icon: <FiMessageSquare />,
      roles: ['admin'],
    },
    {
      label: 'Foydalanuvchilar',
      path: '/admin/profiles',
      icon: <FiUsers />,
      roles: ['admin'],
    },
    {
      label: 'Fayllar',
      path: '/admin/attachments',
      icon: <FiImage />,
      roles: ['admin'],
    },
  ];

  const hasAccess = (roles) => {
    if (roles.includes('admin') && isAdmin()) return true;
    if (roles.includes('moderator') && isModerator()) return true;
    if (roles.includes('publisher') && isPublisher()) return true;
    return false;
  };

  return (
    <aside className="w-64 bg-white border-r border-gray-200 min-h-screen hidden lg:block">
      <div className="p-4">
        <h2 className="text-lg font-bold text-gray-800 mb-4 px-3">Boshqaruv</h2>
        <nav className="space-y-1">
          {menuItems
            .filter((item) => hasAccess(item.roles))
            .map((item) => (
              <NavLink
                key={item.path}
                to={item.path}
                end={item.path === '/admin'}
                className={({ isActive }) =>
                  `flex items-center space-x-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-colors ${
                    isActive
                      ? 'bg-primary-50 text-primary-700'
                      : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900'
                  }`
                }
              >
                <span className="text-lg">{item.icon}</span>
                <span>{item.label}</span>
              </NavLink>
            ))}
        </nav>
      </div>
    </aside>
  );
};

export default Sidebar;
