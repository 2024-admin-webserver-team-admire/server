package post.post.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import post.post.application.result.WriterInfoResult;

public record WriterInfoResponse(
        @Schema(description = "회원 id")
        Long id,
        @Schema(description = "회원 이름")
        String name
) {
    public static WriterInfoResponse from(WriterInfoResult member) {
        return new WriterInfoResponse(member.id(), member.name());
    }
}
