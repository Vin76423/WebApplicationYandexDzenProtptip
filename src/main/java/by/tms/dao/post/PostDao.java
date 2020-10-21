package by.tms.dao.post;

import by.tms.entity.Post;
import java.util.List;

public interface PostDao {
    String GET_ALL_POSTS = "SELECT * FROM posts";
    String GET_POST_BY_ID = "SELECT * FROM posts WHERE id = ?";
    String GET_POST_BY_TITLE = "SELECT * FROM post WHERE title = ?";
    String CREATE_POST = "INSERT INTO posts VALUES(null, ?, ?, ?, ?)";
    String UPDATE_POST_BY_ID = "UPDATE posts SET date = ?, title = ?, massage = ?, user_id = ? WHERE id = ?";

    List<Post> getDemoVersionAllPost();
    List<Post> getAllPosts();
    Post getPostByTitle(String title);
    Post getPostById(long id);
    void createPost(Post post);
    void updatePostById(Post post);
    boolean containPostByTitle(String title);
    boolean containPostById(long id);
}
