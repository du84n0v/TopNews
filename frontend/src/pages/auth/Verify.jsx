import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import authService from '../../api/authService';
import { MdNewspaper } from 'react-icons/md';
import { FiMail, FiKey } from 'react-icons/fi';
import { toast } from 'react-toastify';

const Verify = () => {
  const { verify } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [formData, setFormData] = useState({
    username: location.state?.username || '',
    code: '',
  });
  const [loading, setLoading] = useState(false);
  const [resending, setResending] = useState(false);

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    const result = await verify(formData);
    setLoading(false);
    if (result.success) {
      navigate('/login');
    }
  };

  const handleResend = async () => {
    if (!formData.username) {
      toast.warning("Email kiriting");
      return;
    }
    setResending(true);
    try {
      await authService.resendCode(formData.username);
      toast.success("Kod qayta yuborildi!");
    } catch (error) {
      toast.error("Xatolik yuz berdi");
    } finally {
      setResending(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4">
      <div className="max-w-md w-full">
        <div className="text-center mb-8">
          <Link to="/" className="inline-flex items-center space-x-2">
            <MdNewspaper className="text-primary-600 text-4xl" />
            <span className="text-3xl font-bold text-gray-900">
              Top<span className="text-primary-600">News</span>
            </span>
          </Link>
          <p className="mt-3 text-gray-500">Emailni tasdiqlash</p>
        </div>

        <div className="bg-white rounded-2xl shadow-sm border border-gray-100 p-8">
          <div className="text-center mb-6">
            <div className="w-16 h-16 bg-primary-50 rounded-full flex items-center justify-center mx-auto mb-4">
              <FiMail className="text-primary-600 text-2xl" />
            </div>
            <p className="text-sm text-gray-600">
              Emailingizga yuborilgan tasdiqlash kodini kiriting
            </p>
          </div>

          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <div className="relative">
                <FiMail className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
                <input
                  type="email"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                  className="input-field pl-10"
                  placeholder="email@example.com"
                  required
                />
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Tasdiqlash kodi</label>
              <div className="relative">
                <FiKey className="absolute left-3 top-1/2 -translate-y-1/2 text-gray-400" />
                <input
                  type="text"
                  name="code"
                  value={formData.code}
                  onChange={handleChange}
                  className="input-field pl-10 text-center text-lg tracking-widest"
                  placeholder="000000"
                  required
                />
              </div>
            </div>

            <button
              type="submit"
              disabled={loading}
              className="w-full btn-primary py-3 disabled:opacity-50"
            >
              {loading ? 'Tekshirilmoqda...' : 'Tasdiqlash'}
            </button>
          </form>

          <div className="mt-4 text-center">
            <button
              onClick={handleResend}
              disabled={resending}
              className="text-sm text-primary-600 hover:text-primary-700 font-medium disabled:opacity-50"
            >
              {resending ? 'Yuborilmoqda...' : 'Kodni qayta yuborish'}
            </button>
          </div>

          <div className="mt-6 text-center border-t border-gray-100 pt-4">
            <Link to="/login" className="text-sm text-gray-500 hover:text-primary-600">
              Kirish sahifasiga qaytish
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Verify;
