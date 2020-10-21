package by.tms.service.user;

import by.tms.entity.User;
import java.util.List;

public interface UserService {
    void createUser(User user);
    List<User> getAllUsers();
    User getUserById(long id);
    User getUserByLogin(String login);
    void updateUserById(User user, String field, String value);
    void deleteUserById(long id);
}
