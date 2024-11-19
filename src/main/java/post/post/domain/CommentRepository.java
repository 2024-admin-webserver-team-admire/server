package post.post.domain;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import post.common.exception.type.NotFoundException;
import post.member.domain.Member;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment getById(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("id가 %d인 댓글을 찾을 수 없습니다.".formatted(id))
        );
    }

    List<Comment> findAllByPost(Post post);

    @EntityGraph(attributePaths = {"post"})
    List<Comment> findAllByWriter(Member member);

    void deleteByPost(Post post);
}
