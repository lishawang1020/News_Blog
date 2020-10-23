package NowNewsBlog.service;

import NowNewsBlog.dto.AuthResponse;
import NowNewsBlog.dto.LoginRequest;
import NowNewsBlog.dto.RefreshTokenRequest;
import NowNewsBlog.dto.RegisterRequest;
import NowNewsBlog.exceptions.NowNewsBlogException;
import NowNewsBlog.model.NotificationEmail;
import NowNewsBlog.model.User;
import NowNewsBlog.model.VerificationToken;
import NowNewsBlog.repository.UserRepository;
import NowNewsBlog.repository.VerificationTokenRepository;
import NowNewsBlog.security.JwtProvider;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
@Transactional
public class  AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final MailService mailService;
  private final AuthenticationManager authenticationManager;
  private final JwtProvider jwtProvider;
  private final RefreshTokenService refreshTokenService;

  public void signup(RegisterRequest registerRequest) {
    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setCreated(Instant.now());
    user.setEnabled(false);

    userRepository.save(user);

    String token = generateVerificationToken(user);
    mailService.sendMail(
        new NotificationEmail("Now News Blog - Activate your Account", user.getEmail(),
            "Please use the following link to activate your account: "
                + "http://localhost:8080/api/auth/accountVerification/" + token));
  }

  private String generateVerificationToken(User user) {
    String token = UUID.randomUUID().toString();
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(user);

    verificationTokenRepository.save(verificationToken);
    return token;
  }

  public void verifyAccount(String token) {
    Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
    verificationToken.orElseThrow(() -> new NowNewsBlogException("Invalid Token"));
    fetchUserAndEnable(verificationToken.get());
  }

  @Transactional(readOnly = true)
  public User getCurrentUser() {
    org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
        getContext().getAuthentication().getPrincipal();
    return userRepository.findByUsername(principal.getUsername())
        .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
  }

  private void fetchUserAndEnable(VerificationToken verificationToken) {
    String username = verificationToken.getUser().getUsername();
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new NowNewsBlogException("User name not found - " + username));
    user.setEnabled(true);
    userRepository.save(user);
  }

  public AuthResponse login(LoginRequest loginRequest) {
    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
        loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authenticate);
    String token = jwtProvider.generateToken(authenticate);
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    String formattedDate = formatter.format(Date.from(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())));
    return AuthResponse.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenService.generateRefreshToken().getToken())
        .expiresAt(formattedDate)
        .username(loginRequest.getUsername())
        .build();
  }

  public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
    refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
    String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    String formattedDate = formatter.format(Date.from(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis())));
    return AuthResponse.builder()
        .authenticationToken(token)
        .refreshToken(refreshTokenRequest.getRefreshToken())
        .expiresAt(formattedDate)
        .username(refreshTokenRequest.getUsername())
        .build();
  }

  public boolean isLoggedIn() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
  }
}
