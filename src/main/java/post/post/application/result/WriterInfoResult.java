package post.post.application.result;

import post.member.domain.Member;

public record WriterInfoResult(
        Long id,
        String name
) {
    public static WriterInfoResult from(Member member) {
        return new WriterInfoResult(member.getId(), member.getName());
    }
}
