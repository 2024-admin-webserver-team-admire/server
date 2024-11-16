package post.post.application;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import post.common.exception.type.ConflictException;
import post.common.exception.type.ForbiddenException;
import post.member.domain.Member;
import post.member.domain.MemberRepository;
import post.post.application.command.PostUpdateCommand;
import post.post.application.command.PostWriteCommand;
import post.post.domain.Post;
import post.post.domain.PostRepository;

@Transactional
@SpringBootTest
@DisplayName("PostService 은(는)")
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class PostServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PostRepository postRepository;

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

    @Nested
    class 게시글_수정_시 {

        private Long postId;

        @BeforeEach
        void setUp() {
            memberRepository.save(member1);
            memberRepository.save(member2);
            PostWriteCommand postWriteCommand = new PostWriteCommand(member1, "제목", "내용");
            postId = postService.write(postWriteCommand);
        }

        @Test
        void 내_글이라면_수정_가능() {
            // given
            PostUpdateCommand command = new PostUpdateCommand(postId, member1, "ut", "uc");

            // when
            postService.update(command);

            // then
            Post post = postRepository.getById(postId);
            assertThat(post.getTitle()).isEqualTo("ut");
            assertThat(post.getContent()).isEqualTo("uc");
        }

        @Test
        void 내_게시글이_아니면_수정_불가() {
            // given
            PostUpdateCommand command = new PostUpdateCommand(postId, member2, "ut", "uc");

            // when & then
            Assertions.assertThatThrownBy(() -> {
                postService.update(command);
            }).isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    class 포스트_제거_시 {

        private Long postId;

        @BeforeEach
        void setUp() {
            memberRepository.save(member1);
            memberRepository.save(member2);
            PostWriteCommand postWriteCommand = new PostWriteCommand(member1, "제목", "내용");
            postId = postService.write(postWriteCommand);
        }

        @Test
        void 내_글이라면_제거_가능() {
            // when
            postService.delete(member1, postId);

            // then
            assertThat(postRepository.existsById(postId)).isFalse();
        }

        @Test
        void 내_게시글이_아니면_제거_불가() {
            // when & then
            Assertions.assertThatThrownBy(() -> {
                postService.delete(member2, postId);
            }).isInstanceOf(ForbiddenException.class);
        }
    }

    @Nested
    class 좋아요_테스트 {

        private Long postId;

        @BeforeEach
        void setUp() {
            memberRepository.save(member1);
            PostWriteCommand postWriteCommand = new PostWriteCommand(member1, "제목", "내용");
            postId = postService.write(postWriteCommand);
        }

        @Test
        void 좋아요() {
            // when
            postService.like(member1, postId);

            // then
            Post post = postRepository.getById(postId);
            assertThat(post.getLikeCount()).isEqualTo(1);
        }

        @Test
        void 중복_좋아요시_예외() {
            // given
            postService.like(member1, postId);

            // when
            Assertions.assertThatThrownBy(() -> {
                postService.like(member1, postId);
            }).isInstanceOf(ConflictException.class);

            // then
            Post post = postRepository.getById(postId);
            assertThat(post.getLikeCount()).isEqualTo(1);
        }

        @Test
        void 좋아요_취소() {
            // given
            postService.like(member1, postId);

            // when
            postService.dislike(member1, postId);

            // then
            Post post = postRepository.getById(postId);
            assertThat(post.getLikeCount()).isEqualTo(0);
        }
    }
}
