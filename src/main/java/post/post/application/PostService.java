package post.post.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
}
