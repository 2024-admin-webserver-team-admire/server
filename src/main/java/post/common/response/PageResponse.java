package post.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import org.springframework.data.domain.Page;

public record PageResponse<T>(
        @Schema(description = "내용")
        List<T> content,
        @Schema(description = "전체 페이지 수")
        int totalPage,
        @Schema(description = "현제 페이지 (0부터 시작)")
        int currentPage,
        @Schema(description = "한 페이지 당 조회할 엔티티 크기")
        int pageSize,
        @Schema(description = "전체 엔티티 수")
        int totalElementsCount,
        @Schema(description = "현재 페이지의 엔티티 수")
        int currentElementsCount
) {

    public static <T> PageResponse<T> from(Page<T> result) {
        return new PageResponse<>(
                result.getContent(),
                result.getTotalPages(),
                result.getNumber(),
                result.getSize(),
                (int) result.getTotalElements(),
                result.getNumberOfElements()
        );
    }
}
