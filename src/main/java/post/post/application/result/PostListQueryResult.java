package post.post.application.result;

import java.time.LocalDateTime;
import post.post.domain.Post;

public record PostListQueryResult(
        WriterInfoResult writer,
        Long id,
        String title,
        long likeCount,
        long viewCount,
        LocalDateTime createdDate
) {
    public static PostListQueryResult from(Post post) {
        return new PostListQueryResult(
                WriterInfoResult.from(post.getWriter()),
                post.getId(),
                post.getTitle(),
                post.getLikeCount(),
                post.getViewCount(),
                post.getCreatedDate()
        );
    }
}
