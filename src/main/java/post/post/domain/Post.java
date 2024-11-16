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
public class Post extends RootEntity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    private long likeCount;
    private long viewCount;

    public Post(String title, String content, Member writer) {
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public void validateWriter(Member member) {
        if (!writer.equals(member)) {
            throw new ForbiddenException("해당 게시글에 대한 권한이 없습니다.");
        }
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostLike like(Member member) {
        this.likeCount++;
        return new PostLike(this, member);
    }

    public void dislike() {
        this.likeCount--;
    }
}
