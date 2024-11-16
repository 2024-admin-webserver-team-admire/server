package post.member.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import post.auth.token.TokenResponse;
import post.auth.token.TokenService;
import post.member.application.MemberQueryService;
import post.member.application.MemberService;
import post.member.presentation.request.LoginRequest;
import post.member.presentation.request.SignupRequest;

@RequiredArgsConstructor
@RequestMapping("/members")
@RestController
public class MemberController {

    private final MemberService memberService;
    private final MemberQueryService memberQueryService;
    private final TokenService tokenService;

    @ApiResponses(value = {
            @ApiResponse(description = "아이디가 중복되었다면 true, 중복되지 않았다면 false", responseCode = "200"),
    })
    @Operation(summary = "아이디 중복검사")
    @GetMapping("/check-username-duplicate")
    public ResponseEntity<Boolean> checkDuplicateUsername(@RequestParam("username") String username) {
        boolean duplicatedUsername = memberQueryService.isDuplicatedUsername(username);
        return ResponseEntity.ok(duplicatedUsername);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201"),
            @ApiResponse(responseCode = "409", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "회원가입")
    @PostMapping
    public ResponseEntity<TokenResponse> signup(
            @Valid @RequestBody SignupRequest request
    ) {
        Long memberId = memberService.signup(request.toCommand());
        TokenResponse token = tokenService.createToken(memberId);
        return ResponseEntity.status(201).body(token);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(hidden = true))),
    })
    @Operation(summary = "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> signup(
            @Valid @RequestBody LoginRequest request
    ) {
        Long memberId = memberService.login(request.username(), request.password());
        TokenResponse token = tokenService.createToken(memberId);
        return ResponseEntity.status(200).body(token);
    }
}
