package post.post.application.command;

import post.member.domain.Member;
import post.post.domain.Post;

public record PostWriteCommand(
        Member member,
        String title,
        String content
) {
    public Post toPost() {
        return new Post(title, content, member);
    }
}
