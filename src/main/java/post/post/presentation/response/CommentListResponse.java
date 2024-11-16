package post.post.presentation.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import post.post.domain.Comment;
import post.post.domain.Post;

public record CommentListResponse(
        @Schema(description = "게시글 정보")
        PostResponse postResponse,
        @Schema(description = "댓글들")
        List<CommentResponse> comments
) {

    public static CommentListResponse of(Post post, List<Comment> comments) {
        return new CommentListResponse(
                PostResponse.from(post),
                comments.stream()
                        .map(CommentResponse::from)
                        .toList()
        );
    }

    public record PostResponse(
            @Schema(description = "게시글 작성자 정보")
            WriterInfoResponse writer,
            @Schema(description = "게시글 id")
            Long id,
            @Schema(description = "게시글 제목")
            String title,
            @Schema(description = "좋아요 수")
            long likeCount,
            @Schema(description = "조회수")
            long viewCount,
            @Schema(description = "댓글수")
            long commentCount,
            @Schema(description = "작성일", example = "2024-10-04T14:30:00")
            LocalDateTime createdDate
    ) {
        public static PostResponse from(Post post) {
            return new PostResponse(
                    WriterInfoResponse.from(post.getWriter()),
                    post.getId(),
                    post.getTitle(),
                    post.getLikeCount(),
                    post.getViewCount(),
                    post.getCommentCount(),
                    post.getCreatedDate()
            );
        }
    }

    public record CommentResponse(
            @Schema(description = "게시글 작성자 정보")
            WriterInfoResponse writer,
            @Schema(description = "댓글 id")
            Long id,
            @Schema(description = "댓글 내용")
            String content,
            @Schema(description = "작성일", example = "2024-10-04T14:30:00")
            LocalDateTime createdDate
    ) {
        public static CommentResponse from(Comment comment) {
            return new CommentResponse(
                    WriterInfoResponse.from(comment.getWriter()),
                    comment.getId(),
                    comment.getContent(),
                    comment.getCreatedDate()
            );
        }
    }
}
