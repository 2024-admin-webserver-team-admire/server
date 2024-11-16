package post.post.presentation;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.PATH;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import post.auth.Auth;
import post.member.domain.Member;
import post.post.application.CommentQueryService;
import post.post.application.CommentService;
import post.post.domain.Comment;
import post.post.domain.Post;
import post.post.presentation.request.CommentUpdateRequest;
import post.post.presentation.request.CommentWriteRequest;
import post.post.presentation.response.CommentListResponse;
import post.post.presentation.response.MyPageCommentListResponse;

@Tag(name = "댓글 API")
@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentQueryService commentQueryService;

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
    })
    @Operation(summary = "댓글 작성")
    @PostMapping
    public ResponseEntity<Long> write(
            @Auth Member member,
            @Valid @RequestBody CommentWriteRequest request
    ) {
        Long commentId = commentService.write(member, request.postId(), request.content());
        return ResponseEntity.status(201).body(commentId);
    }

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "댓글 수정")
    @PutMapping("/{id}")
    public ResponseEntity<Long> update(
            @Auth Member member,
            @Parameter(in = PATH, required = true, description = "댓글 ID")
            @PathVariable("id") Long id,
            @Valid @RequestBody CommentUpdateRequest request
    ) {
        commentService.update(member, id, request.content());
        return ResponseEntity.status(200).build();
    }

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "댓글 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(
            @Auth Member member,
            @Parameter(in = PATH, required = true, description = "댓글 ID")
            @PathVariable("id") Long id
    ) {
        commentService.delete(member, id);
        return ResponseEntity.status(200).build();
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @Operation(summary = "게시글에 달린 댓글 조회")
    @GetMapping("/posts/{postId}")
    public ResponseEntity<CommentListResponse> findAll(
            @Parameter(in = PATH, required = true, description = "게시글 ID")
            @PathVariable("postId") Long postId
    ) {
        Pair<Post, List<Comment>> result = commentQueryService.findAllByPostId(postId);
        return ResponseEntity.ok(CommentListResponse.of(result.getFirst(), result.getSecond()));
    }

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @Operation(summary = "내가 작성한 댓글 조회")
    @GetMapping("/my/write")
    public ResponseEntity<List<MyPageCommentListResponse>> findAllMyComment(@Auth Member member) {
        List<MyPageCommentListResponse> results = commentQueryService.findAllMyComment(member)
                .stream()
                .map(MyPageCommentListResponse::from)
                .toList();
        return ResponseEntity.ok(results);
    }
}
