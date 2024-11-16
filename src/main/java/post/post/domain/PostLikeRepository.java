package post.post.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import post.common.exception.type.NotFoundException;
import post.member.domain.Member;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByMemberAndPost(Member member, Post post);

    Optional<PostLike> findByMemberAndPost(Member member, Post post);

    default PostLike getByMemberAndPost(Member member, Post post) {
        return findByMemberAndPost(member, post)
                .orElseThrow(() -> new NotFoundException("좋아요를 누르지 않았습니다."));
    }
}
