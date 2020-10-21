package by.tms.dao.comment;

import by.tms.entity.Comment;
import java.util.List;

public interface CommentDao {
    String GET_ALL_COMMENTS_BY_POST_ID = "SELECT * FROM comments WHERE post_id = ?";
    String ADD_COMMENT_TO_POST = "INSERT INTO comments VALUES(null, ?, ? , ?, ?)";

    List<Comment> getAllCommentByPostId(long postId);
    void addCommentToPost(Comment comment, long postId);
}
