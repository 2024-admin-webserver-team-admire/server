package post.member.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String hashedPassword;
    private String name;
    private int age;
    private LocalDate birthday;
    private String email;

    public Member(String username, String hashedPassword, String name, int age, LocalDate birthday, String email) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.email = email;
    }

    public void signup(MemberValidator validator) {
        validator.validateSignup(username);
    }
}
