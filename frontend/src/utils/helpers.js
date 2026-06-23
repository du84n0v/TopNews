import { format, formatDistanceToNow } from 'date-fns';
import { uz } from 'date-fns/locale';

// Sanani formatlash
export const formatDate = (dateString) => {
  if (!dateString) return '';
  try {
    const date = new Date(dateString);
    return format(date, 'dd.MM.yyyy HH:mm');
  } catch {
    return dateString;
  }
};

// Necha vaqt oldin
export const timeAgo = (dateString) => {
  if (!dateString) return '';
  try {
    const date = new Date(dateString);
    return formatDistanceToNow(date, { addSuffix: true });
  } catch {
    return dateString;
  }
};

// Qisqa sanani formatlash
export const formatShortDate = (dateString) => {
  if (!dateString) return '';
  try {
    const date = new Date(dateString);
    return format(date, 'dd MMM, yyyy');
  } catch {
    return dateString;
  }
};

// Matnni qisqartirish
export const truncateText = (text, maxLength = 150) => {
  if (!text) return '';
  if (text.length <= maxLength) return text;
  return text.substring(0, maxLength) + '...';
};

// Rasm URL olish
export const getImageUrl = (attachInfo) => {
  if (!attachInfo) return '/placeholder.jpg';
  if (attachInfo.url) return attachInfo.url;
  if (attachInfo.id) return `/api/v1/attaches/open/${attachInfo.id}`;
  return '/placeholder.jpg';
};

// Status rangini olish
export const getStatusColor = (status) => {
  switch (status) {
    case 'PUBLISHED':
      return 'bg-green-100 text-green-800';
    case 'NOT_PUBLISHED':
      return 'bg-yellow-100 text-yellow-800';
    default:
      return 'bg-gray-100 text-gray-800';
  }
};

// Status nomini olish
export const getStatusName = (status) => {
  switch (status) {
    case 'PUBLISHED':
      return 'Nashr qilingan';
    case 'NOT_PUBLISHED':
      return 'Nashr qilinmagan';
    default:
      return status;
  }
};

// Role nomini olish
export const getRoleName = (role) => {
  switch (role) {
    case 'ROLE_ADMIN':
      return 'Admin';
    case 'ROLE_MODERATOR':
      return 'Moderator';
    case 'ROLE_PUBLISHER':
      return 'Publisher';
    case 'ROLE_USER':
      return 'Foydalanuvchi';
    default:
      return role;
  }
};
