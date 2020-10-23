package NowNewsBlog.controller;

import static org.springframework.http.HttpStatus.OK;

import NowNewsBlog.dto.AuthResponse;
import NowNewsBlog.dto.LoginRequest;
import NowNewsBlog.dto.RefreshTokenRequest;
import NowNewsBlog.dto.RegisterRequest;
import NowNewsBlog.exceptions.NowNewsBlogException;
import NowNewsBlog.service.AuthService;
import NowNewsBlog.service.RefreshTokenService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

  private final AuthService authService;
  private final RefreshTokenService refreshTokenService;

  @PostMapping("/signup")
  public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest)
      throws NowNewsBlogException {
    authService.signup(registerRequest);
    return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
  }

  @GetMapping("accountVerification/{token}")
  public ResponseEntity<String> verifyAccount(@PathVariable String token) {
    authService.verifyAccount(token);
    return new ResponseEntity<>("Account Activation Successful", HttpStatus.OK);
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody LoginRequest loginRequest) {
    return authService.login(loginRequest);
  }

  @PostMapping("refresh/token")
  public AuthResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
    return authService.refreshToken(refreshTokenRequest);
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
    refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
    return ResponseEntity.status(OK).body("Refresh Token Deleted Successfully");
  }
}
