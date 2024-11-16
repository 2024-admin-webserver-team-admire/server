package post.member.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;
import post.member.application.command.SignupCommand;

public record SignupRequest(
        @Schema(example = "mallang")
        @NotBlank(message = "아이디는 필수 값입니다.") String username,

        @Schema(example = "password123@")
        @NotBlank(message = "비밀번호는 필수 값입니다.") String plainPassword,

        @Schema(example = "신동훈")
        @NotBlank(message = "이름은 필수 값입니다.") String name,

        @Schema(example = "2000-10-04")
        @Past(message = "생일은 과거 날짜여야 합니다.") LocalDate birthday,

        @Schema(example = "example@domain.com")
        @Email(message = "이메일 형식이 올바르지 않습니다.") String email
) {
    public SignupCommand toCommand() {
        return new SignupCommand(
                username,
                plainPassword,
                name,
                birthday,
                email
        );
    }
}
