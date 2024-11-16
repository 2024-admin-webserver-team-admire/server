package post.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import post.member.application.command.SignupCommand;
import post.member.domain.Member;
import post.member.domain.MemberRepository;
import post.member.domain.MemberValidator;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;

    public Long signup(SignupCommand command) {
        Member member = command.toMember();
        member.signup(memberValidator);
        return memberRepository.save(member)
                .getId();
    }

    public Long login(String username, String password) {
        Member member = memberRepository.getByUsername(username);
        member.login(password);
        return member.getId();
    }
}
