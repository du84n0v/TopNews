import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import {
  FiFileText, FiUsers, FiFolder, FiLayers,
  FiMapPin, FiMessageSquare, FiTrendingUp
} from 'react-icons/fi';

const Dashboard = () => {
  const { user, isAdmin, isModerator, isPublisher } = useAuth();

  const adminCards = [
    {
      title: 'Maqolalar',
      icon: <FiFileText className="text-2xl" />,
      link: '/admin/articles',
      color: 'bg-blue-50 text-blue-600',
      description: "Maqolalarni boshqarish",
      roles: ['admin', 'moderator', 'publisher'],
    },
    {
      title: 'Kategoriyalar',
      icon: <FiFolder className="text-2xl" />,
      link: '/admin/categories',
      color: 'bg-green-50 text-green-600',
      description: "Kategoriyalarni boshqarish",
      roles: ['admin'],
    },
    {
      title: "Bo'limlar",
      icon: <FiLayers className="text-2xl" />,
      link: '/admin/sections',
      color: 'bg-purple-50 text-purple-600',
      description: "Bo'limlarni boshqarish",
      roles: ['admin'],
    },
    {
      title: 'Regionlar',
      icon: <FiMapPin className="text-2xl" />,
      link: '/admin/regions',
      color: 'bg-orange-50 text-orange-600',
      description: "Regionlarni boshqarish",
      roles: ['admin'],
    },
    {
      title: 'Foydalanuvchilar',
      icon: <FiUsers className="text-2xl" />,
      link: '/admin/profiles',
      color: 'bg-pink-50 text-pink-600',
      description: "Foydalanuvchilarni boshqarish",
      roles: ['admin'],
    },
    {
      title: 'Izohlar',
      icon: <FiMessageSquare className="text-2xl" />,
      link: '/admin/comments',
      color: 'bg-yellow-50 text-yellow-600',
      description: "Izohlarni boshqarish",
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
    <div>
      {/* Welcome */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">
          Xush kelibsiz, {user?.name}!
        </h1>
        <p className="text-gray-500 mt-1">Boshqaruv paneli</p>
      </div>

      {/* Quick Stats */}
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
        {adminCards
          .filter((card) => hasAccess(card.roles))
          .map((card) => (
            <Link
              key={card.title}
              to={card.link}
              className="card p-6 hover:shadow-lg transition-shadow group"
            >
              <div className="flex items-center space-x-4">
                <div className={`w-14 h-14 rounded-xl flex items-center justify-center ${card.color}`}>
                  {card.icon}
                </div>
                <div>
                  <h3 className="font-semibold text-gray-900 group-hover:text-primary-600 transition-colors">
                    {card.title}
                  </h3>
                  <p className="text-sm text-gray-500">{card.description}</p>
                </div>
              </div>
            </Link>
          ))}
      </div>
    </div>
  );
};

export default Dashboard;
