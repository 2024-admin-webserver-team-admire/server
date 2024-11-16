package post.post.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import post.common.exception.type.NotFoundException;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post getById(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("id가 %d인 포스트를 찾을 수 없습니다.".formatted(id))
        );
    }

    Page<Post> findAllByOrderByIdDesc(Pageable pageable);
}
