package post.post.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import post.common.exception.type.NotFoundException;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment getById(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("id가 %d인 댓글을 찾을 수 없습니다.".formatted(id))
        );
    }

    List<Comment> findAllByPost(Post post);
}
