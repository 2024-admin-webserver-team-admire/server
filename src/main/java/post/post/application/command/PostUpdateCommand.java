package post.post.application.command;

import post.member.domain.Member;

public record PostUpdateCommand(
        Long postId,
        Member member,
        String title,
        String content
) {
}
