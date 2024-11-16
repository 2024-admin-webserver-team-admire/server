package post.post.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentWriteRequest(
        @Schema(example = "게시글 Id")
        @NotBlank(message = "게시글 Id는 필수 값입니다.") Long postId,

        @Schema(example = "내용")
        @NotBlank(message = "내용은 필수 값입니다.") String content
) {
}
