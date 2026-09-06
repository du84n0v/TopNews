import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import articleService from '../../api/articleService';
import commentService from '../../api/commentService';
import likeService from '../../api/likeService';
import { useAuth } from '../../context/AuthContext';
import ArticleCard from '../../components/ArticleCard';
import LoadingSpinner from '../../components/LoadingSpinner';
import { formatDate, timeAgo } from '../../utils/helpers';
import {
  FiEye, FiShare2, FiThumbsUp, FiMessageSquare,
  FiSend, FiCornerDownRight
} from 'react-icons/fi';
import { toast } from 'react-toastify';

const ArticleDetail = () => {
  const { articleId } = useParams();
  const { isAuthenticated, user } = useAuth();
  const [article, setArticle] = useState(null);
  const [comments, setComments] = useState([]);
  const [mostRead, setMostRead] = useState([]);
  const [loading, setLoading] = useState(true);
  const [commentText, setCommentText] = useState('');
  const [replyTo, setReplyTo] = useState(null);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    loadArticle();
  }, [articleId]);

  const loadArticle = async () => {
    try {
      setLoading(true);
      const data = await articleService.getById(articleId);
      setArticle(data);

      // Ko'rish sonini oshirish
      await articleService.increaseViewCount(articleId);

      // Izohlarni olish
      const commentsData = await commentService.getArticleComments(articleId);
      setComments(commentsData || []);

      // Ko'p o'qilganlar
      const mostReadData = await articleService.getMostRead(articleId, 1, 4);
      setMostRead(mostReadData.content || []);
    } catch (error) {
      console.error('Error:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleLike = async () => {
    if (!isAuthenticated) {
      toast.warning("Like bosish uchun tizimga kiring");
      return;
    }
    try {
      await likeService.likeArticle(articleId);
      toast.success("Like bosildi!");
      loadArticle();
    } catch (error) {
      toast.error("Xatolik yuz berdi");
    }
  };

  const handleShare = async () => {
    await articleService.increaseShareCount(articleId);
    if (navigator.share) {
      navigator.share({
        title: article.title,
        url: window.location.href,
      });
    } else {
      navigator.clipboard.writeText(window.location.href);
      toast.success("Havola nusxalandi!");
    }
  };

  const handleCommentSubmit = async (e) => {
    e.preventDefault();
    if (!commentText.trim()) return;
    if (!isAuthenticated) {
      toast.warning("Izoh yozish uchun tizimga kiring");
      return;
    }

    setSubmitting(true);
    try {
      await commentService.create({
        content: commentText,
        articleId: articleId,
        replyId: replyTo,
      });
      setCommentText('');
      setReplyTo(null);
      toast.success("Izoh qo'shildi!");
      const commentsData = await commentService.getArticleComments(articleId);
      setComments(commentsData || []);
    } catch (error) {
      toast.error("Izoh qo'shishda xatolik");
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <LoadingSpinner size="lg" text="Maqola yuklanmoqda..." />;
  if (!article) {
    return (
      <div className="max-w-4xl mx-auto px-4 py-16 text-center">
        <h2 className="text-2xl font-bold text-gray-600">Maqola topilmadi</h2>
        <Link to="/" className="btn-primary mt-4 inline-block">Bosh sahifaga</Link>
      </div>
    );
  }

  return (
    <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* Main Content */}
        <div className="lg:col-span-2">
          {/* Breadcrumb */}
          <nav className="flex items-center space-x-2 text-sm text-gray-500 mb-4">
            <Link to="/" className="hover:text-primary-600">Bosh sahifa</Link>
            <span>/</span>
            {article.categoryList?.[0] && (
              <>
                <Link
                  to={`/category/${article.categoryList[0].id}`}
                  className="hover:text-primary-600"
                >
                  {article.categoryList[0].name}
                </Link>
                <span>/</span>
              </>
            )}
            <span className="text-gray-700 truncate">{article.title}</span>
          </nav>

          {/* Article Header */}
          <h1 className="text-3xl md:text-4xl font-bold text-gray-900 mb-4">
            {article.title}
          </h1>

          <p className="text-lg text-gray-600 mb-6">{article.description}</p>

          {/* Meta info */}
          <div className="flex flex-wrap items-center gap-4 text-sm text-gray-500 mb-6 pb-6 border-b">
            <span>{formatDate(article.publishedDate)}</span>
            <span className="flex items-center space-x-1">
              <FiEye /> <span>{article.viewCount || 0} ko'rilgan</span>
            </span>
            <span className="flex items-center space-x-1">
              <FiThumbsUp /> <span>{article.likeCount || 0}</span>
            </span>
            <span className="flex items-center space-x-1">
              <FiShare2 /> <span>{article.sharedCount || 0} ulashilgan</span>
            </span>
          </div>

          {/* Article Content */}
          <div
            className="prose prose-lg max-w-none mb-8"
            dangerouslySetInnerHTML={{ __html: article.content }}
          />

          {/* Tags & Categories */}
          <div className="flex flex-wrap gap-2 mb-6">
            {article.categoryList?.map((cat) => (
              <Link
                key={cat.id}
                to={`/category/${cat.id}`}
                className="px-3 py-1 bg-primary-50 text-primary-700 rounded-full text-sm font-medium hover:bg-primary-100"
              >
                {cat.name}
              </Link>
            ))}
            {article.sectionList?.map((sec) => (
              <Link
                key={sec.id}
                to={`/section/${sec.id}`}
                className="px-3 py-1 bg-gray-100 text-gray-700 rounded-full text-sm font-medium hover:bg-gray-200"
              >
                {sec.name}
              </Link>
            ))}
          </div>

          {/* Action Buttons */}
          <div className="flex items-center space-x-4 mb-8 pb-8 border-b">
            <button
              onClick={handleLike}
              className="flex items-center space-x-2 px-4 py-2 rounded-lg border border-gray-200 hover:bg-primary-50 hover:border-primary-200 transition-colors"
            >
              <FiThumbsUp className="text-primary-600" />
              <span className="text-sm font-medium">Yoqdi ({article.likeCount || 0})</span>
            </button>
            <button
              onClick={handleShare}
              className="flex items-center space-x-2 px-4 py-2 rounded-lg border border-gray-200 hover:bg-gray-50 transition-colors"
            >
              <FiShare2 className="text-gray-600" />
              <span className="text-sm font-medium">Ulashish</span>
            </button>
          </div>

          {/* Comments Section */}
          <section className="mb-8">
            <h3 className="text-xl font-bold text-gray-900 mb-6 flex items-center space-x-2">
              <FiMessageSquare />
              <span>Izohlar ({comments.length})</span>
            </h3>

            {/* Comment Form */}
            <form onSubmit={handleCommentSubmit} className="mb-8">
              {replyTo && (
                <div className="flex items-center space-x-2 mb-2 text-sm text-primary-600">
                  <FiCornerDownRight />
                  <span>Javob yozmoqdasiz</span>
                  <button
                    type="button"
                    onClick={() => setReplyTo(null)}
                    className="text-red-500 hover:text-red-700"
                  >
                    Bekor qilish
                  </button>
                </div>
              )}
              <div className="flex space-x-3">
                <div className="w-10 h-10 rounded-full bg-primary-100 flex items-center justify-center flex-shrink-0">
                  <span className="text-primary-600 font-semibold text-sm">
                    {user?.name?.[0] || '?'}
                  </span>
                </div>
                <div className="flex-1">
                  <textarea
                    value={commentText}
                    onChange={(e) => setCommentText(e.target.value)}
                    placeholder={isAuthenticated ? "Izoh yozing..." : "Izoh yozish uchun tizimga kiring"}
                    disabled={!isAuthenticated}
                    className="input-field resize-none h-20"
                  />
                  <button
                    type="submit"
                    disabled={!isAuthenticated || submitting || !commentText.trim()}
                    className="btn-primary mt-2 flex items-center space-x-2 disabled:opacity-50"
                  >
                    <FiSend />
                    <span>{submitting ? 'Yuborilmoqda...' : 'Yuborish'}</span>
                  </button>
                </div>
              </div>
            </form>

            {/* Comments List */}
            <div className="space-y-6">
              {comments.map((comment) => (
                <div key={comment.id} className="flex space-x-3">
                  <div className="w-9 h-9 rounded-full bg-gray-200 flex items-center justify-center flex-shrink-0">
                    <span className="text-gray-600 font-semibold text-xs">
                      {comment.profileDto?.name?.[0] || '?'}
                    </span>
                  </div>
                  <div className="flex-1">
                    <div className="bg-gray-50 rounded-lg p-3">
                      <div className="flex items-center space-x-2 mb-1">
                        <span className="font-semibold text-sm text-gray-900">
                          {comment.profileDto?.name} {comment.profileDto?.surname}
                        </span>
                        <span className="text-xs text-gray-400">
                          {timeAgo(comment.createdDate)}
                        </span>
                      </div>
                      <p className="text-sm text-gray-700">{comment.content}</p>
                    </div>
                    <div className="flex items-center space-x-4 mt-1 ml-3">
                      <button
                        onClick={() => setReplyTo(comment.id)}
                        className="text-xs text-gray-500 hover:text-primary-600"
                      >
                        Javob berish
                      </button>
                      <span className="text-xs text-gray-400 flex items-center space-x-1">
                        <FiThumbsUp size={11} />
                        <span>{comment.likeCount || 0}</span>
                      </span>
                    </div>
                  </div>
                </div>
              ))}
              {comments.length === 0 && (
                <p className="text-center text-gray-400 py-6">
                  Hali izoh yo'q. Birinchi bo'ling!
                </p>
              )}
            </div>
          </section>
        </div>

        {/* Sidebar - Most Read */}
        <aside className="lg:col-span-1">
          <div className="sticky top-24">
            <h3 className="text-lg font-bold text-gray-900 mb-4">
              Ko'p o'qilganlar
            </h3>
            <div className="space-y-4">
              {mostRead.map((article) => (
                <ArticleCard
                  key={article.articleId}
                  article={article}
                  variant="horizontal"
                />
              ))}
            </div>

            {/* Region */}
            {article.region && (
              <div className="mt-8 p-4 bg-gray-50 rounded-xl">
                <h4 className="font-semibold text-gray-700 mb-2">Region</h4>
                <Link
                  to={`/region/${article.region.id}`}
                  className="text-primary-600 hover:underline"
                >
                  {article.region.name}
                </Link>
              </div>
            )}
          </div>
        </aside>
      </div>
    </div>
  );
};

export default ArticleDetail;
