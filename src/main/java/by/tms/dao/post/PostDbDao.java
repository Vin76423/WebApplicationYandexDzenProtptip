package by.tms.dao.post;

import by.tms.dao.exception.NoResultException;
import by.tms.entity.Post;
import by.tms.entity.User;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostDbDao implements PostDao {
    private DataSource dataSource;

    public PostDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Post getPost(ResultSet rs) throws SQLException {
        return new Post(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("massage"),
                new User(rs.getLong("user_id")),
                rs.getTimestamp("date").toLocalDateTime());
    }

    private Post getDemoVersionPost(ResultSet rs) throws SQLException {
        return new Post(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getTimestamp("date").toLocalDateTime());
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_POSTS);
            while (rs.next()) {
                posts.add(getPost(rs));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public List<Post> getDemoVersionAllPost() {
        List<Post> posts = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_POSTS);
            while (rs.next()) {
                posts.add(getDemoVersionPost(rs));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post getPostByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title is null!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_POST_BY_TITLE);
            preparedStatement.setString(1, title);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getPost(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoResultException("Not found post with such title!");
    }

    @Override
    public Post getPostById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_POST_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return getPost(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoResultException("Not found post with such title!");
    }

    @Override
    public void createPost(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("User is null!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE_POST);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(post.getDate()));
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getMassage());
            preparedStatement.setLong(4, post.getAuthor().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePostById(Post post) {
        if (post == null) {
            throw new IllegalArgumentException("User is null!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_POST_BY_ID);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(post.getDate()));
            preparedStatement.setString(2, post.getTitle());
            preparedStatement.setString(3, post.getMassage());
            preparedStatement.setLong(4, post.getAuthor().getId());
            preparedStatement.setLong(5, post.getId());
            if (preparedStatement.executeUpdate() < 0) {
                throw new NoResultException("Post with such id does not exist!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean containPostByTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title is null!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_POST_BY_TITLE);
            preparedStatement.setString(1, title);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoResultException();
    }

    @Override
    public boolean containPostById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id is not correct!");
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_POST_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoResultException();
    }
}
