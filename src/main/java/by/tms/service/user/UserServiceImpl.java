package by.tms.service.user;

import by.tms.dao.user.UserDao;
import by.tms.entity.User;
import by.tms.service.user.exception.DuplicateUserException;
import by.tms.service.user.exception.NotFoundUserByIdException;
import by.tms.service.user.exception.NotFoundUserByLoginException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void createUser(User user) {
        if (userDao.containUserByLogin(user.getLogin())) {
            throw new DuplicateUserException();
        }
        userDao.createUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(long id) {
        if (userDao.containUserById(id)) {
            return userDao.getUserById(id);
        }
        throw new NotFoundUserByIdException();
    }

    @Override
    public User getUserByLogin(String login) {
        if (userDao.containUserByLogin(login)) {
            return userDao.getUserByLogin(login);
        }
        throw new NotFoundUserByLoginException();
    }

    @Override
    public void updateUserById(User user, String field, String value) {
        if (field == null || value == null) {
            throw new IllegalArgumentException("Arguments is null!");
        }
        if (!userDao.containUserById(user.getId())) {
            throw new NotFoundUserByIdException();
        }
        switch (field) {
            case "name":
                user.setName(value);
                break;
            case "login":
                user.setLogin(value);
                break;
            case "password":
                user.setPassword(value);
                break;
            case "age":
                user.setAge(Integer.parseInt(value));
                break;
        }
        userDao.updateUserById(user);
    }

    @Override
    public void deleteUserById(long id) {
        if (userDao.containUserById(id)) {
            userDao.deleteUserById(id);
        }
        throw new NotFoundUserByIdException();
    }
}
