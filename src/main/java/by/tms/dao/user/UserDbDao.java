package by.tms.dao.user;

import by.tms.dao.exception.NoResultException;
import by.tms.entity.User;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDbDao implements UserDao {
    private DataSource dataSource;

    public UserDbDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private User createUserWithOutId(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("login"),
                rs.getString("password"),
                rs.getInt("age")
        );
    }

    @Override
    public void createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Argument 'user' is null!");
        }
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(CREATE_USER);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getAge());
            if (statement.executeUpdate() < 0) {
                throw new NoResultException("Such user already exist!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_USERS);
            while (rs.next()) {
                users.add(createUserWithOutId(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (users.isEmpty()) {
            throw new NoResultException("List users is empty!");
        }
        return users;
    }

    @Override
    public User getUserById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Argument 'id' is negative or 0!");
        }
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID);
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createUserWithOutId(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoResultException("Such user does not exist!");
    }

    @Override
    public User getUserByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Argument 'login' is null!");
        }
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN);
            statement.setString(1, login);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createUserWithOutId(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new NoResultException("Such user does not exist!");
    }

    @Override
    public void updateUserById(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Argument 'user' is null!");
        }
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_USER_BY_ID);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setInt(4, user.getAge());
            statement.setLong(5, user.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Argument 'id' is negative or 0!");
        }
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID);
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean containUserById(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Argument 'id' is negative or 0!");
        }
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_ID);
            statement.setLong(1, id);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean containUserByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Argument 'login' is null!");
        }
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN);
            statement.setString(1, login);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
