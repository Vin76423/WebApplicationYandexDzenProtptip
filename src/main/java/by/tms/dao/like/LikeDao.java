package by.tms.dao.like;

import by.tms.entity.Like;
import java.util.List;

public interface LikeDao {
    String GET_ALL_LIKES_BY_POST_ID = "SELECT * FROM `post-user-like` WHERE post_id = ?";
    String ADD_LIKE_BY_POST_ID_AND_USER_ID = "INSERT INTO `post-user-like` VALUES(?, ?)";
    String GET_LIKE_BY_POST_ID_AND_USER_ID = "SELECT * FROM `post-user-like` WHERE post_id = ? AND user_id = ?";
    String DELETE_LIKE_BY_POST_ID_AND_USER_ID = "DELETE FROM `post-user-like` WHERE post_id = ? AND user_id = ?";

    List<Like> getAllLikesByPostId(long postId);
    void addLikeToPost(Like like, long postId);
    void deleteLikeToPost(Like like, long postId);
    boolean containLike(Like like, long postID);

}
