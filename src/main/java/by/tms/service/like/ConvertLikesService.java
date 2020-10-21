package by.tms.service.like;

import by.tms.entity.Like;
import java.util.List;

public interface ConvertLikesService {
    List<Like> convert(List<Like> likes);
}
