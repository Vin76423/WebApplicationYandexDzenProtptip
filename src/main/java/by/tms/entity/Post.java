package by.tms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Post {
    private static final String DATE_PATTERN = "yyyy-MM-dd  HH:mm:ss";
    private static long idInc = 1;
    private long id = idInc++;
    private String title;
    private String massage;
    private User author;
    private LocalDateTime date = LocalDateTime.now();
    private List<Like> likes = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();

    /**
     * Constructor for getting post from data base:
     * */
    public Post(long id, String title, String massage, User author, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.massage = massage;
        this.author = author;
        this.date = date;
    }

    /**
     * Constructor for getting demo-post from data base:
     * */
    public Post(long id, String title, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.date = date;
    }


    public void setComment(Comment comment) {
        this.comments.add(comment);
    }

    public void setLike(Like like) {
        this.likes.add(like);
    }

    public String showDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return date.format(formatter);
    }
}
