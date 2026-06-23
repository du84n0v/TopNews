import { Routes, Route } from 'react-router-dom';
import { ProtectedRoute, AdminRoute, ModeratorRoute } from './components/ProtectedRoute';
import Layout from './components/Layout';
import AdminLayout from './components/AdminLayout';

// Public Pages
import Home from './pages/public/Home';
import ArticleDetail from './pages/public/ArticleDetail';
import CategoryArticles from './pages/public/CategoryArticles';
import SectionArticles from './pages/public/SectionArticles';
import RegionArticles from './pages/public/RegionArticles';
import Categories from './pages/public/Categories';
import Sections from './pages/public/Sections';
import Regions from './pages/public/Regions';

// Auth Pages
import Login from './pages/auth/Login';
import Register from './pages/auth/Register';
import Verify from './pages/auth/Verify';

// User Pages
import Profile from './pages/user/Profile';
import Settings from './pages/user/Settings';

// Admin Pages
import Dashboard from './pages/admin/Dashboard';
import ArticleManagement from './pages/admin/ArticleManagement';
import CategoryManagement from './pages/admin/CategoryManagement';
import SectionManagement from './pages/admin/SectionManagement';
import RegionManagement from './pages/admin/RegionManagement';
import ProfileManagement from './pages/admin/ProfileManagement';
import CommentManagement from './pages/admin/CommentManagement';

// Other
import NotFound from './pages/NotFound';

function App() {
  return (
    <Routes>
      {/* Auth pages (no layout) */}
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/verify" element={<Verify />} />

      {/* Public pages with Layout */}
      <Route element={<Layout />}>
        <Route path="/" element={<Home />} />
        <Route path="/article/:articleId" element={<ArticleDetail />} />
        <Route path="/category/:categoryId" element={<CategoryArticles />} />
        <Route path="/section/:sectionId" element={<SectionArticles />} />
        <Route path="/region/:regionId" element={<RegionArticles />} />
        <Route path="/categories" element={<Categories />} />
        <Route path="/sections" element={<Sections />} />
        <Route path="/regions" element={<Regions />} />

        {/* Protected user pages */}
        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <Profile />
            </ProtectedRoute>
          }
        />
        <Route
          path="/settings"
          element={
            <ProtectedRoute>
              <Settings />
            </ProtectedRoute>
          }
        />
      </Route>

      {/* Admin pages with AdminLayout - protected */}
      <Route element={<AdminLayout />}>
        <Route path="/admin" element={<ProtectedRoute><Dashboard /></ProtectedRoute>} />
        <Route path="/admin/articles" element={<ProtectedRoute><ArticleManagement /></ProtectedRoute>} />
        <Route
          path="/admin/categories"
          element={
            <AdminRoute>
              <CategoryManagement />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/sections"
          element={
            <AdminRoute>
              <SectionManagement />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/regions"
          element={
            <AdminRoute>
              <RegionManagement />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/profiles"
          element={
            <AdminRoute>
              <ProfileManagement />
            </AdminRoute>
          }
        />
        <Route
          path="/admin/comments"
          element={
            <AdminRoute>
              <CommentManagement />
            </AdminRoute>
          }
        />
      </Route>

      {/* 404 */}
      <Route element={<Layout />}>
        <Route path="*" element={<NotFound />} />
      </Route>
    </Routes>
  );
}

export default App;
