package post.post.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import post.member.domain.Member;
import post.member.domain.MemberRepository;
import post.post.application.command.PostWriteCommand;

@Transactional
@SpringBootTest
@DisplayName("PostService 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class PostServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostService postService;

    private Member member1 = new Member("member1", "pwd", "1", LocalDate.of(2000, 10, 4), "1@a.b");
    private Member member2 = new Member("member2", "pwd", "2", LocalDate.of(2000, 10, 4), "2@a.b");

    @Test
    void 게시글_작성() {
        // given
        memberRepository.save(member1);
        PostWriteCommand postWriteCommand = new PostWriteCommand(member1, "제목", "내용");

        // when
        Long postId = postService.write(postWriteCommand);

        // then
        assertThat(postId).isNotNull();
    }
}
