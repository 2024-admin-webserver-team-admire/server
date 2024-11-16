package post.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import post.member.domain.MemberRepository;

@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberRepository memberRepository;

    public boolean isDuplicatedUsername(String username) {
        return memberRepository.existsByUsername(username);
    }
}
