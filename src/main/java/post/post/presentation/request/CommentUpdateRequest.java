package post.post.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequest(
        @Schema(example = "내용")
        @NotBlank(message = "내용은 필수 값입니다.") String content
) {
}
