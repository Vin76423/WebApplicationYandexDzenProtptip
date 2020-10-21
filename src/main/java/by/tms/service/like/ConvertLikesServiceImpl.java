package by.tms.service.like;

import by.tms.dao.user.UserDao;
import by.tms.entity.Like;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConvertLikesServiceImpl implements ConvertLikesService {
    private UserDao userDao;

    public ConvertLikesServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<Like> convert(List<Like> likes) {
        if (likes == null) {
            throw new IllegalArgumentException("Likes is null!");
        }
        List<Like> updatedLikes = new ArrayList<>();
        for (Like like : likes) {
            long userId = like.getAuthor().getId();
            updatedLikes.add(new Like(userDao.getUserById(userId)));
        }
        return updatedLikes;
    }
}
