package post.post.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import post.common.exception.type.ConflictException;
import post.member.domain.Member;
import post.post.application.command.PostUpdateCommand;
import post.post.application.command.PostWriteCommand;
import post.post.domain.Post;
import post.post.domain.PostLike;
import post.post.domain.PostLikeRepository;
import post.post.domain.PostRepository;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public Long write(PostWriteCommand command) {
        Post post = command.toPost();
        return postRepository.save(post)
                .getId();
    }

    @Transactional
    public void update(PostUpdateCommand command) {
        Post post = postRepository.getById(command.postId());
        post.validateWriter(command.member());
        post.update(command.title(), command.content());
    }

    public void delete(Member member, Long postId) {
        Post post = postRepository.getById(postId);
        post.validateWriter(member);
        postRepository.delete(post);
    }

    @Transactional
    public void like(Member member, Long postId) {
        Post post = postRepository.getByIdWithLock(postId);
        if (postLikeRepository.existsByMemberAndPost(member, post)) {
            throw new ConflictException("이미 좋아요 누른 게시글입니다.");
        }
        PostLike like = post.like(member);
        postLikeRepository.save(like);
    }

    @Transactional
    public void dislike(Member member, Long postId) {
        Post post = postRepository.getByIdWithLock(postId);
        PostLike postLike = postLikeRepository.getByMemberAndPost(member, post);
        post.dislike();
        postLikeRepository.delete(postLike);
    }
}
