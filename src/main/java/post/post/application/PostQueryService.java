package post.post.application;

import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import post.member.domain.Member;
import post.post.domain.Post;
import post.post.domain.PostRepository;

@RequiredArgsConstructor
@Service
public class PostQueryService {

    private final PostRepository postRepository;

    public Page<Post> getPosts(Pageable pageable) {
        return postRepository.findAllByOrderByIdDesc(pageable);
    }

    public Post getPost(Long id) {
        return postRepository.getById(id);
    }

    public List<Post> findAllMyPost(Member member) {
        return postRepository.findAllByWriter(member);
    }

    public List<Post> findAllLiked(Member member) {
        return postRepository.findAllLiked(member);
    }
}
