package post.post.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import post.post.application.result.PostListQueryResult;
import post.post.application.result.PostSingleQueryResult;
import post.post.domain.PostRepository;

@RequiredArgsConstructor
@Service
public class PostQueryService {

    private final PostRepository postRepository;

    public Page<PostListQueryResult> getPosts(Pageable pageable) {
        return postRepository.findAllByOrderByIdDesc(pageable)
                .map(PostListQueryResult::from);
    }

    public PostSingleQueryResult getPost(Long id) {
        return PostSingleQueryResult.from(postRepository.getById(id));
    }
}
