package post.post.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import post.member.domain.Member;
import post.post.domain.Comment;
import post.post.domain.CommentRepository;
import post.post.domain.Post;
import post.post.domain.PostRepository;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long write(Member member, Long postId, String content) {
        Post post = postRepository.getById(postId);
        Comment comment = post.writeComment(member, content);
        return commentRepository.save(comment).getId();
    }

    @Transactional
    public void update(Member member, Long commentId, String content) {
        Comment comment = commentRepository.getById(commentId);
        comment.validateWriter(member);
        comment.update(content);
    }

    @Transactional
    public void delete(Member member, Long commentId) {
        Comment comment = commentRepository.getById(commentId);
        comment.validateWriter(member);
        comment.getPost().decreaseCommentCount();
        commentRepository.delete(comment);
    }
}
