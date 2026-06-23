import { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import profileService from '../../api/profileService';
import attachService from '../../api/attachService';
import { getRoleName, getImageUrl } from '../../utils/helpers';
import { FiUser, FiMail, FiCamera, FiEdit2 } from 'react-icons/fi';
import { toast } from 'react-toastify';

const Profile = () => {
  const { user, setUser } = useAuth();
  const [uploading, setUploading] = useState(false);

  const handlePhotoUpload = async (e) => {
    const file = e.target.files[0];
    if (!file) return;

    setUploading(true);
    try {
      const attach = await attachService.upload(file);
      await profileService.updatePhoto(attach.id);
      const updatedUser = { ...user, content: { id: attach.id, url: attach.url } };
      setUser(updatedUser);
      localStorage.setItem('user', JSON.stringify(updatedUser));
      toast.success("Rasm yangilandi!");
    } catch (error) {
      toast.error("Rasm yuklashda xatolik");
    } finally {
      setUploading(false);
    }
  };

  return (
    <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <h1 className="page-title">Profilim</h1>

      <div className="card p-8">
        <div className="flex flex-col md:flex-row items-center md:items-start space-y-6 md:space-y-0 md:space-x-8">
          {/* Avatar */}
          <div className="relative">
            <div className="w-32 h-32 rounded-full overflow-hidden bg-gray-200 flex items-center justify-center">
              {user?.content ? (
                <img
                  src={getImageUrl(user.content)}
                  alt="Profile"
                  className="w-full h-full object-cover"
                />
              ) : (
                <FiUser className="text-4xl text-gray-400" />
              )}
            </div>
            <label className="absolute bottom-0 right-0 w-10 h-10 bg-primary-600 rounded-full flex items-center justify-center cursor-pointer hover:bg-primary-700 transition-colors shadow-lg">
              <FiCamera className="text-white" />
              <input
                type="file"
                accept="image/*"
                onChange={handlePhotoUpload}
                className="hidden"
                disabled={uploading}
              />
            </label>
          </div>

          {/* Info */}
          <div className="flex-1 text-center md:text-left">
            <h2 className="text-2xl font-bold text-gray-900">
              {user?.name} {user?.surname}
            </h2>
            <p className="text-gray-500 flex items-center justify-center md:justify-start space-x-1 mt-1">
              <FiMail className="text-gray-400" />
              <span>{user?.username}</span>
            </p>

            {/* Roles */}
            <div className="flex flex-wrap gap-2 mt-4 justify-center md:justify-start">
              {user?.roleList?.map((role) => (
                <span
                  key={role}
                  className="px-3 py-1 bg-primary-50 text-primary-700 rounded-full text-sm font-medium"
                >
                  {getRoleName(role)}
                </span>
              ))}
            </div>

            {/* Status */}
            <div className="mt-4">
              <span className={`px-3 py-1 rounded-full text-sm font-medium ${
                user?.status === 'ACTIVE'
                  ? 'bg-green-100 text-green-700'
                  : 'bg-gray-100 text-gray-700'
              }`}>
                {user?.status === 'ACTIVE' ? 'Faol' : user?.status}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
