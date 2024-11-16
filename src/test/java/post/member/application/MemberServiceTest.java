package post.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import post.common.exception.type.ConflictException;
import post.member.application.command.SignupCommand;

@Transactional
@SpringBootTest
@DisplayName("MemberService 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void 회원가입_성공() {
        // given
        SignupCommand signupCommand = new SignupCommand(
                "user",
                "pwd",
                "name",
                10,
                LocalDate.of(2000, 10, 4),
                "aa@naver.com"
        );

        // when
        Long signup = memberService.signup(signupCommand);

        // then
        assertThat(signup).isNotNull();
    }

    @Test
    void 회원가입_시_아이디가_중복되면_예외() {
        // given
        SignupCommand signupCommand = new SignupCommand(
                "user",
                "pwd",
                "name",
                10,
                LocalDate.of(2000, 10, 4),
                "aa@naver.com"
        );
        memberService.signup(signupCommand);

        // when & then
        Assertions.assertThatThrownBy(() -> {
            memberService.signup(signupCommand);
        }).isInstanceOf(ConflictException.class);
    }
}
