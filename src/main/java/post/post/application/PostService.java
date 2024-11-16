package post.post.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import post.member.domain.Member;
import post.post.application.command.PostUpdateCommand;
import post.post.application.command.PostWriteCommand;
import post.post.domain.Post;
import post.post.domain.PostRepository;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

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
}
