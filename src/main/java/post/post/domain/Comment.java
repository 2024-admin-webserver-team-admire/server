package post.post.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import post.common.domain.RootEntity;
import post.common.exception.type.ForbiddenException;
import post.member.domain.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Comment extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private String content;

    public Comment(Post post, Member writer, String content) {
        this.post = post;
        this.writer = writer;
        this.content = content;
    }

    public void validateWriter(Member member) {
        if (!writer.equals(member)) {
            throw new ForbiddenException("해당 댓글에 대한 권한이 없습니다.");
        }
    }

    public void update(String content) {
        this.content = content;
    }
}
