import api from './index';

const likeService = {
  // Maqolaga like
  likeArticle: async (articleId) => {
    const response = await api.post(`/article-like/like/${articleId}`);
    return response.data;
  },

  // Maqolaga dislike
  dislikeArticle: async (articleId) => {
    const response = await api.post(`/article-like/dislike/${articleId}`);
    return response.data;
  },

  // Izohga like
  likeComment: async (commentId) => {
    const response = await api.post(`/comment-like/like/${commentId}`);
    return response.data;
  },
};

export default likeService;
