package by.tms.service.post;

import by.tms.dao.comment.CommentDao;
import by.tms.dao.like.LikeDao;
import by.tms.dao.post.PostDao;
import by.tms.dao.user.UserDao;
import by.tms.entity.Comment;
import by.tms.entity.Like;
import by.tms.entity.Post;
import by.tms.service.post.exception.NotFoundPostByIdException;
import by.tms.service.post.exception.NotFoundPostByTitleException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private UserDao userDao;
    private PostDao postDao;
    private CommentDao commentDao;
    private LikeDao likeDao;

    public PostServiceImpl(UserDao userDao, PostDao postDao, CommentDao commentDao, LikeDao likeDao) {
        this.userDao = userDao;
        this.postDao = postDao;
        this.commentDao = commentDao;
        this.likeDao = likeDao;
    }

    private void setAuthorInComments(List<Comment> comments) {
        for (Comment comment : comments) {
            long authorId = comment.getAuthor().getId();
            comment.setAuthor(userDao.getUserById(authorId));
        }
    }

    @Override
    public List<Post> getDemoVersionAllPosts() {
        List<Post> posts = postDao.getDemoVersionAllPost();
        return posts;
    }

    @Override
    public Post getPostByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title is null!");
        } else if (!postDao.containPostByTitle(title)) {
            throw new NotFoundPostByTitleException();
        }
        Post post = postDao.getPostByTitle(title);
        List<Comment> comments = commentDao.getAllCommentByPostId(post.getId());
        if (!comments.isEmpty()) {
            setAuthorInComments(comments);
        }
        post.setComments(comments);
        post.setLikes(likeDao.getAllLikesByPostId(post.getId()));
        return post;
    }

    @Override
    public Post GetPostById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id is not correct!");
        } else if (!postDao.containPostById(id)) {
            throw new NotFoundPostByIdException();
        }
        Post post = postDao.getPostById(id);
        List<Comment> comments = commentDao.getAllCommentByPostId(post.getId());
        if (!comments.isEmpty()) {
            setAuthorInComments(comments);
        }
        post.setComments(comments);
        post.setLikes(likeDao.getAllLikesByPostId(post.getId()));
        return post;
    }

    @Override
    public void createPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("Post is null!");
        }
        postDao.createPost(post);
    }

    @Override
    public void setComment(Comment comment, long postId) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment is null!");
        }
        if (postId <= 0) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        commentDao.addCommentToPost(comment, postId);
    }

    @Override
    public void setLike(Like like, long postId) {
        if (like == null) {
            throw new IllegalArgumentException("Comment is null!");
        }
        if (postId <= 0) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        if (likeDao.containLike(like, postId)) {
            likeDao.deleteLikeToPost(like, postId);
            return;
        }
        likeDao.addLikeToPost(like, postId);
    }
}
