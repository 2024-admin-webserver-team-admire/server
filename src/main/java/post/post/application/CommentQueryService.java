package post.post.application;

import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import post.member.domain.Member;
import post.post.domain.Comment;
import post.post.domain.CommentRepository;
import post.post.domain.Post;
import post.post.domain.PostRepository;

@RequiredArgsConstructor
@Service
public class CommentQueryService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public Pair<Post, List<Comment>> findAllByPostId(Long postId) {
        Post post = postRepository.getById(postId);
        return Pair.of(post, commentRepository.findAllByPost(post));
    }

    public List<Comment> findAllMyComment(Member member) {
        return commentRepository.findAllByWriter(member);
    }
}
