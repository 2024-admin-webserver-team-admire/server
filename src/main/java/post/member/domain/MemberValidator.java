package post.member.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import post.common.exception.type.ConflictException;

@RequiredArgsConstructor
@Component
public class MemberValidator {

    private final MemberRepository memberRepository;

    public void validateSignup(String username) {
        if (memberRepository.existsByUsername(username)) {
            throw new ConflictException("아이디가 중복되었습니다. 다른 아이디를 사용해주세요.");
        }
    }
}
