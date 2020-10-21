package by.tms.dao.comment;

import by.tms.entity.Comment;
import by.tms.entity.User;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentDbDao implements CommentDao {
    private DataSource dataSource;

    public CommentDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Comment getComment(ResultSet rs) throws SQLException {
        return new Comment(
                rs.getLong("id"),
                rs.getString("massage"),
                new User(rs.getLong("user_id")),
                rs.getTimestamp("date").toLocalDateTime());
    }

    @Override
    public List<Comment> getAllCommentByPostId(long postId) {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_COMMENTS_BY_POST_ID);
            preparedStatement.setLong(1, postId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                comments.add(getComment(rs));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    @Override
    public void addCommentToPost(Comment comment, long postId) {
        if (comment == null) {
            throw new IllegalArgumentException("Comment is null!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_COMMENT_TO_POST);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(comment.getDate()));
            preparedStatement.setString(2, comment.getMassage());
            preparedStatement.setLong(3, comment.getAuthor().getId());
            preparedStatement.setLong(4, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
