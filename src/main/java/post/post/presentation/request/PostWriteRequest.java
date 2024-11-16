package post.post.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import post.member.domain.Member;
import post.post.application.command.PostWriteCommand;

public record PostWriteRequest(
        @Schema(example = "제목")
        @NotBlank(message = "제목은 필수 값입니다.") String title,

        @Schema(example = "내용")
        @NotBlank(message = "내용은 필수 값입니다.") String content
) {
    public PostWriteCommand toCommand(Member member) {
        return new PostWriteCommand(member, title, content);
    }
}
