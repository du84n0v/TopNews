import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

// Faqat login qilgan foydalanuvchilar uchun
export const ProtectedRoute = ({ children }) => {
  const { isAuthenticated } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  return children;
};

// Faqat Admin uchun
export const AdminRoute = ({ children }) => {
  const { isAuthenticated, isAdmin } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (!isAdmin()) {
    return <Navigate to="/" replace />;
  }

  return children;
};

// Moderator yoki Admin uchun
export const ModeratorRoute = ({ children }) => {
  const { isAuthenticated, isAdmin, isModerator } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (!isAdmin() && !isModerator()) {
    return <Navigate to="/" replace />;
  }

  return children;
};

// Publisher yoki Admin uchun
export const PublisherRoute = ({ children }) => {
  const { isAuthenticated, isAdmin, isPublisher } = useAuth();

  if (!isAuthenticated) {
    return <Navigate to="/login" replace />;
  }

  if (!isAdmin() && !isPublisher()) {
    return <Navigate to="/" replace />;
  }

  return children;
};
