package NowNewsBlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
  private String authenticationToken;
  private String refreshToken;
  private String expiresAt;
  private String username;
}
