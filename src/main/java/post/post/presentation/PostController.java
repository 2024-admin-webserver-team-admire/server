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
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import post.auth.Auth;
import post.common.response.PageResponse;
import post.member.domain.Member;
import post.post.application.PostQueryService;
import post.post.application.PostService;
import post.post.application.command.PostUpdateCommand;
import post.post.application.command.PostWriteCommand;
import post.post.domain.Post;
import post.post.presentation.request.PostUpdateRequest;
import post.post.presentation.request.PostWriteRequest;
import post.post.presentation.response.PostListQueryResponse;
import post.post.presentation.response.PostSingleQueryResponse;

@Slf4j
@Tag(name = "게시글 API")
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    public static final String VIEW_POST_HEADER_NAME = "viewedPosts";

    private final PostService postService;
    private final PostQueryService postQueryService;

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
    })
    @Operation(summary = "게시글 작성")
    @PostMapping
    public ResponseEntity<Long> write(
            @Auth Member member,
            @Valid @RequestBody PostWriteRequest request
    ) {
        PostWriteCommand command = request.toCommand(member);
        Long postId = postService.write(command);
        return ResponseEntity.status(201).body(postId);
    }

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "게시글 수정")
    @PutMapping("/{postId}")
    public ResponseEntity<Long> update(
            @Auth Member member,
            @Parameter(in = PATH, required = true, description = "게시글 ID")
            @PathVariable("postId") Long postId,
            @Valid @RequestBody PostUpdateRequest request
    ) {
        PostUpdateCommand command = request.toCommand(member, postId);
        postService.update(command);
        return ResponseEntity.status(200).build();
    }

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "게시글 삭제")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Long> delete(
            @Auth Member member,
            @Parameter(in = PATH, required = true, description = "게시글 ID")
            @PathVariable("postId") Long postId
    ) {
        postService.delete(member, postId);
        return ResponseEntity.status(200).build();
    }

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "게시글 좋아요")
    @PostMapping("/{postId}/like")
    public void like(
            @Auth Member member,
            @Parameter(in = PATH, required = true, description = "게시글 ID")
            @PathVariable("postId") Long postId
    ) {
        postService.like(member, postId);
    }

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "게시글 좋아요 취소")
    @DeleteMapping("/{postId}/dislike")
    public void dislike(
            @Auth Member member,
            @Parameter(in = PATH, required = true, description = "게시글 ID")
            @PathVariable("postId") Long postId
    ) {
        postService.dislike(member, postId);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @Operation(summary = "게시글 목록 조회")
    @GetMapping
    public ResponseEntity<PageResponse<PostListQueryResponse>> findAll(
            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<PostListQueryResponse> results = postQueryService.getPosts(PageRequest.of(page, size))
                .map(PostListQueryResponse::from);
        return ResponseEntity.ok(PageResponse.from(results));
    }

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @Operation(summary = "내가 작성한 게시글 조회")
    @GetMapping("/my/write")
    public ResponseEntity<List<PostListQueryResponse>> findAllMyPost(@Auth Member member) {
        List<PostListQueryResponse> results = postQueryService.findAllMyPost(member)
                .stream()
                .map(PostListQueryResponse::from)
                .toList();
        return ResponseEntity.ok(results);
    }

    @SecurityRequirement(name = "JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200")
    })
    @Operation(summary = "내가 좋아요한 게시글 조회")
    @GetMapping("/my/like")
    public ResponseEntity<List<PostListQueryResponse>> findAllLiked(@Auth Member member) {
        List<PostListQueryResponse> results = postQueryService.findAllLiked(member)
                .stream()
                .map(PostListQueryResponse::from)
                .toList();
        return ResponseEntity.ok(results);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "게시글 단일 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<PostSingleQueryResponse> findById(
            @Parameter(in = PATH, required = true, description = "게시글 ID")
            @PathVariable("postId") Long postId,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Post post = postQueryService.getPost(postId);
        List<String> viewedPosts = getViewPosts(request);
        log.info("postId: {}, viewedPosts: {}, firstView: {}",
                postId, viewedPosts, firstView(postId, viewedPosts));
        if (firstView(postId, viewedPosts)) {
            postService.increaseViewCount(postId);
            viewedPosts.add(postId.toString());
            log.info("add view post cookies");
            addViewPosts(viewedPosts, response);
        }
        return ResponseEntity.ok(PostSingleQueryResponse.from(post));
    }

    private List<String> getViewPosts(HttpServletRequest request) {
        String header = request.getHeader(VIEW_POST_HEADER_NAME);
        System.out.println(header);
        if (header == null || header.isBlank()) {
            return new ArrayList<>();
        }
        return new ArrayList<>(List.of(header.split("_")));
    }

    private boolean firstView(Long postId, List<String> viewedPosts) {
        String postIdString = postId.toString();
        return !viewedPosts.contains(postIdString);
    }

    private void addViewPosts(List<String> viewedPosts, HttpServletResponse response) {
        String updated = String.join("_", viewedPosts);
        log.info("updated view posts: {}", updated);
        response.addHeader(VIEW_POST_HEADER_NAME, updated);
    }
}
