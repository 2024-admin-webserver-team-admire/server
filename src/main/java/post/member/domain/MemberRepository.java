package post.member.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import post.common.exception.type.NotFoundException;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByUsername(String username);

    Optional<Member> findByUsername(String username);

    default Member getByUsername(String username) {
        return findByUsername(username).orElseThrow(
                () -> new NotFoundException("주어진 username 을 가진 회원정보를 찾을 수 없습니다."));
    }
}
