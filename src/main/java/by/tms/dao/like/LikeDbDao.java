package by.tms.dao.like;

import by.tms.dao.exception.NoResultException;
import by.tms.entity.Like;
import by.tms.entity.User;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class LikeDbDao implements LikeDao {
    private DataSource dataSource;

    public LikeDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Like getLike(ResultSet rs) throws SQLException {
        return new Like(new User(rs.getLong("user_id")));
    }

    @Override
    public List<Like> getAllLikesByPostId(long postId) {
        if (postId <= 0) {
            throw new IllegalArgumentException("Id is negative or 0!");
        }
        List<Like> likes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_LIKES_BY_POST_ID);
            preparedStatement.setLong(1, postId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                likes.add(getLike(rs));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }

    @Override
    public void addLikeToPost(Like like, long postId) {
        if (like == null) {
            throw new IllegalArgumentException("Like is null!");
        }
        if (postId <= 0) {
            throw new IllegalArgumentException("Id is negative or 0!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_LIKE_BY_POST_ID_AND_USER_ID);
            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, like.getAuthor().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteLikeToPost(Like like, long postId) {
        if (postId <= 0) {
            throw new IllegalArgumentException("Id is negative or 0!");
        }
        if (like == null) {
            throw new IllegalArgumentException("Like is null!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_LIKE_BY_POST_ID_AND_USER_ID);
            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, like.getAuthor().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean containLike(Like like, long postId) {
        if (like == null) {
            throw new IllegalArgumentException("Like is null!");
        }
        if (postId <= 0) {
            throw new IllegalArgumentException("Id is negative or 0!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_LIKE_BY_POST_ID_AND_USER_ID);
            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, like.getAuthor().getId());
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoResultException();
    }
}
