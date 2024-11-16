package post.post.application.result;

import java.time.LocalDateTime;
import post.post.domain.Post;

public record PostSingleQueryResult(
        WriterInfoResult writer,
        Long id,
        String title,
        String content,
        long likeCount,
        long viewCount,
        LocalDateTime createdDate
) {
    public static PostSingleQueryResult from(Post post) {
        return new PostSingleQueryResult(
                WriterInfoResult.from(post.getWriter()),
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getLikeCount(),
                post.getViewCount(),
                post.getCreatedDate()
        );
    }
}
