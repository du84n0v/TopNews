import { Link } from 'react-router-dom';
import { getImageUrl, truncateText, timeAgo } from '../utils/helpers';
import { FiClock, FiEye } from 'react-icons/fi';

const ArticleCard = ({ article, variant = 'default' }) => {
  const imageUrl = getImageUrl(article.content);

  if (variant === 'featured') {
    return (
      <Link to={`/article/${article.articleId}`} className="block group">
        <div className="card overflow-hidden">
          <div className="relative h-64 md:h-80">
            <img
              src={imageUrl}
              alt={article.title}
              className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
              onError={(e) => { e.target.src = 'https://via.placeholder.com/800x400?text=TopNews'; }}
            />
            <div className="absolute inset-0 bg-gradient-to-t from-black/70 to-transparent" />
            <div className="absolute bottom-0 left-0 right-0 p-6">
              <h2 className="text-2xl font-bold text-white mb-2 group-hover:text-primary-200 transition-colors">
                {article.title}
              </h2>
              <p className="text-gray-200 text-sm line-clamp-2">
                {truncateText(article.description, 120)}
              </p>
              <div className="flex items-center space-x-4 mt-3 text-gray-300 text-xs">
                <span className="flex items-center space-x-1">
                  <FiClock />
                  <span>{timeAgo(article.publishedDate)}</span>
                </span>
              </div>
            </div>
          </div>
        </div>
      </Link>
    );
  }

  if (variant === 'horizontal') {
    return (
      <Link to={`/article/${article.articleId}`} className="block group">
        <div className="card overflow-hidden flex flex-row">
          <div className="w-32 h-24 md:w-48 md:h-32 flex-shrink-0">
            <img
              src={imageUrl}
              alt={article.title}
              className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
              onError={(e) => { e.target.src = 'https://via.placeholder.com/400x200?text=TopNews'; }}
            />
          </div>
          <div className="p-3 md:p-4 flex flex-col justify-center">
            <h3 className="text-sm md:text-base font-semibold text-gray-900 group-hover:text-primary-600 transition-colors line-clamp-2">
              {article.title}
            </h3>
            <p className="text-xs text-gray-500 mt-1 hidden md:block line-clamp-2">
              {truncateText(article.description, 80)}
            </p>
            <span className="text-xs text-gray-400 mt-2 flex items-center space-x-1">
              <FiClock size={12} />
              <span>{timeAgo(article.publishedDate)}</span>
            </span>
          </div>
        </div>
      </Link>
    );
  }

  // Default card
  return (
    <Link to={`/article/${article.articleId}`} className="block group">
      <div className="card overflow-hidden h-full flex flex-col">
        <div className="relative h-48 overflow-hidden">
          <img
            src={imageUrl}
            alt={article.title}
            className="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
            onError={(e) => { e.target.src = 'https://via.placeholder.com/400x200?text=TopNews'; }}
          />
        </div>
        <div className="p-4 flex flex-col flex-1">
          <h3 className="text-lg font-semibold text-gray-900 group-hover:text-primary-600 transition-colors line-clamp-2 mb-2">
            {article.title}
          </h3>
          <p className="text-sm text-gray-500 line-clamp-2 flex-1">
            {truncateText(article.description, 100)}
          </p>
          <div className="flex items-center justify-between mt-3 text-xs text-gray-400">
            <span className="flex items-center space-x-1">
              <FiClock size={12} />
              <span>{timeAgo(article.publishedDate)}</span>
            </span>
            {article.viewCount && (
              <span className="flex items-center space-x-1">
                <FiEye size={12} />
                <span>{article.viewCount}</span>
              </span>
            )}
          </div>
        </div>
      </div>
    </Link>
  );
};

export default ArticleCard;
