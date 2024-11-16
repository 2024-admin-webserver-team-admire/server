package post.member.application.command;

import java.time.LocalDate;
import post.common.security.Sha256;
import post.member.domain.Member;

public record SignupCommand(
        String username,
        String plainPassword,
        String name,
        int age,
        LocalDate birthday,
        String email
) {
    public Member toMember() {
        return new Member(
                username,
                Sha256.encrypt(plainPassword),
                name,
                age,
                birthday,
                email
        );
    }
}
