package post.post.domain;

import jakarta.persistence.LockModeType;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import post.common.exception.type.NotFoundException;
import post.member.domain.Member;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post getById(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("id가 %d인 포스트를 찾을 수 없습니다.".formatted(id))
        );
    }

    Page<Post> findAllByOrderByIdDesc(Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Post p WHERE p.id = :postId")
    Post getByIdWithLock(Long postId);

    List<Post> findAllByWriter(Member member);

    @Query("SELECT p FROM Post p JOIN PostLike pl ON pl.post = p WHERE pl.member = :member")
    List<Post> findAllLiked(Member member);
}
