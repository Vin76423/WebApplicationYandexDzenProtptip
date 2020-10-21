package by.tms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private static long idInc = 1;
    private long id = idInc++;
    @NotEmpty(message = "Name is empty!")
    @NotBlank(message = "Name is empty!")
    private String name;
    @NotEmpty(message = "Login is empty!")
    @NotBlank(message = "Login is empty!")
    private String login;
    @NotEmpty(message = "Password is empty!")
    @NotBlank(message = "Password is empty!")
    private String password;
    private int age;


    public User(long id) {
        this.id = id;
    }
}
