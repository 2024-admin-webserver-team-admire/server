package post.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.Period;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import post.common.domain.RootEntity;
import post.common.exception.type.UnAuthorizedException;
import post.common.security.Sha256;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String hashedPassword;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    private String email;

    public Member(String username, String hashedPassword, String name, LocalDate birthday, String email) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.name = name;
        this.age = calculateAge(birthday);
        this.birthday = birthday;
        this.email = email;
    }

    private int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(birthDate, currentDate);
        return period.getYears();
    }

    public void signup(MemberValidator validator) {
        validator.validateSignup(username);
    }

    public void login(String plainPassword) {
        if (!this.hashedPassword.equals(Sha256.encrypt(plainPassword))) {
            throw new UnAuthorizedException("비밀번호가 잘못되었습니다.");
        }
    }
}
