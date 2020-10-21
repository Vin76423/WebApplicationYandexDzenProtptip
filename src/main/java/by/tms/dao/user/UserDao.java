package by.tms.dao.user;

import by.tms.entity.User;
import java.util.List;


public interface UserDao {
    String CREATE_USER = "INSERT INTO `users` VALUES(null, ?, ?, ?, ?)";
    String GET_ALL_USERS = "SELECT * FROM `users`";
    String GET_USER_BY_ID = "SELECT * FROM `users` WHERE id = ?";
    String GET_USER_BY_LOGIN = "SELECT * FROM `users` WHERE `login` = ?";
    String UPDATE_USER_BY_ID = "UPDATE `users` SET `name` = ?, `login` = ?, `password` = ?, `age` = ? WHERE id = ?";
    String DELETE_USER_BY_ID = "DELETE FROM `users` WHERE id = ?";

    void createUser(User user);
    List<User> getAllUsers();
    User getUserById(long id);
    User getUserByLogin(String login);
    void updateUserById(User user);
    void deleteUserById(long id);
    boolean containUserById(long id);
    boolean containUserByLogin(String login);
}
