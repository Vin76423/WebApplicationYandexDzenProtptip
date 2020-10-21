package by.tms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private static final String DATE_PATTERN = "yyyy-MM-dd  HH:mm:ss";
    private static long idInc = 1;
    private long id = idInc++;
    private String massage;
    private User author;
    private LocalDateTime date = LocalDateTime.now();

    public String showDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return date.format(formatter);
    }
}
