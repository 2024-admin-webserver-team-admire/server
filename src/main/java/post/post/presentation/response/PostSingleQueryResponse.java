package post.post.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import post.post.application.result.PostSingleQueryResult;
import post.post.domain.Post;

public record PostSingleQueryResponse(
        @Schema(description = "작성자 정보")
        WriterInfoResponse writer,
        @Schema(description = "게시글 id")
        Long id,
        @Schema(description = "게시글 제목")
        String title,
        @Schema(description = "게시글 내용")
        String content,
        @Schema(description = "좋아요 수")
        long likeCount,
        @Schema(description = "조회수")
        long viewCount,
        @Schema(description = "작성일", example = "2024-10-04T14:30:00")
        LocalDateTime createdDate
) {
    public static PostSingleQueryResponse from(PostSingleQueryResult result) {
        return new PostSingleQueryResponse(
                WriterInfoResponse.from(result.writer()),
                result.id(),
                result.title(),
                result.content(),
                result.likeCount(),
                result.viewCount(),
                result.createdDate()
        );
    }
}
