package post.post.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentWriteRequest(
        @Schema(example = "게시글 Id")
        @NotNull(message = "게시글 Id는 필수 값입니다.") Long postId,

        @Schema(example = "내용")
        @NotBlank(message = "내용은 필수 값입니다.") String content
) {
}
