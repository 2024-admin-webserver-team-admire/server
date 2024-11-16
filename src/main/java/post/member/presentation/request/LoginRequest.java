package post.member.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @Schema(example = "mallang")
        @NotBlank(message = "아이디는 필수 값입니다.") String username,

        @Schema(example = "password123@")
        @NotBlank(message = "비밀번호는 필수 값입니다.") String password
) {
}
