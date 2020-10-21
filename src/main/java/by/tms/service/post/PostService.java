package by.tms.service.post;

import by.tms.entity.Comment;
import by.tms.entity.Like;
import by.tms.entity.Post;
import java.util.List;

public interface PostService {
    List<Post> getDemoVersionAllPosts();
    Post getPostByTitle(String title);
    Post GetPostById(long id);
    void createPost(Post post);
//    void updatePost(Post post);
    void setComment(Comment comment, long postId);
    void setLike(Like like, long postId);
}
