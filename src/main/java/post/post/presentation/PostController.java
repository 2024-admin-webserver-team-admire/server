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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import post.auth.Auth;
import post.member.domain.Member;
import post.post.application.PostService;
import post.post.application.command.PostUpdateCommand;
import post.post.application.command.PostWriteCommand;
import post.post.presentation.request.PostUpdateRequest;
import post.post.presentation.request.PostWriteRequest;

@Tag(name = "게시글 API")
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

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
}