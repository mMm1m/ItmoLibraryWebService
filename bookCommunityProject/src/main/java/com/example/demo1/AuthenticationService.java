package com.example.demo1;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.demo1.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@Builder
@AllArgsConstructor
public class AuthenticationService {
	
	private final UserRepository repository;
	private final TokenRepository tokenRepository;
	private final JwtService service;
	private final AuthenticationManager manager;
	private final PasswordEncoder encoder;
	private final EmailValidator validator;
	private final EmailSender emailSender;
	private final ConfirmationToken confirmationToken;
	private final ConfirmationTokenService confirmationTokenService;
	
	
	public AuthenticationResponse register(RegisterRequest request)
	{
		var isCorrectMail = validator.test(request.getEmail());
		var isCorrectLogin = validator.test(request.getLogin());
		if(!isCorrectMail || !isCorrectLogin) 
			throw new IllegalStateException("email or login isn't correct");
		
		var user = User.builder()
				.name(request.getName())
				.login(request.getLogin())
				.mail(request.getEmail())
				.password(encoder.encode(request.getPassword()))
				//.encodedPassword(request.getPassword())
				//.role(request.getRole())
				.role(Role.USER)
				.build();
		var savedUser = repository.save(user);
	    var jwtToken = service.generateToken(user);
	    var refreshToken = service.generateRefreshToken(user);
	    saveUserToken(savedUser, jwtToken);
	    
	    String link = "http://localhost:8080/auth/register/confirm?token=" + jwtToken;
        emailSender.send(
                request.getEmail(),
                buildEmail(request.getName(), link));
	    
	    return AuthenticationResponse.builder()
	        .accessToken(jwtToken)
	            .refreshToken(refreshToken)
	        .build();
	}
	
	public AuthenticationResponse authenticate(AuthenticationRequest request)
	{
		manager.authenticate(new UsernamePasswordAuthenticationToken(
				request.getLogin(),
				request.getPassword()
				));
		var user = repository.findByLogin(request.getLogin())
		        .orElseThrow();
		var jwtToken = service.generateToken(user);
	    var refreshToken = service.generateRefreshToken(user);
	    // change to: String token = generateConfirmationToken()
	    revokeAllUserTokens(user);
	    saveUserToken(user, jwtToken);
	    return AuthenticationResponse.builder()
	        .accessToken(jwtToken)
	            .refreshToken(refreshToken)
	        .build();
	}
	
	private void saveUserToken(User user, String jwtToken) {
		    var token = Token.builder()
		        .user(user)
		        .token(jwtToken)
		        .tokenType(TokenType.BEARER)
		        .expired(false)
		        .revoked(false)
		        .build();
		    tokenRepository.save(token);
		 }

		  private void revokeAllUserTokens(User user) {
		    var validUserTokens = tokenRepository.findAllValidTokenByUserId(user.getId());
		    if (!validUserTokens.isEmpty()) return;
		    validUserTokens.forEach(token ->
		    {token.setExpired(true);
		    token.setRevoked(true);
		    });
		    tokenRepository.saveAll(validUserTokens);
		  }
		  
		  public void refreshToken(
		          HttpServletRequest request,
		          HttpServletResponse response
		  ) throws IOException {
		    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		    final String refreshToken;
		    final String userEmail;
		    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
		      return;
		    }
		    refreshToken = authHeader.substring(7);
		    userEmail = service.extractUserLogin(refreshToken);
		    if (userEmail != null) {
		      var user = this.repository.findByLogin(userEmail)
		              .orElseThrow();
		      if (service.isTokenValid(refreshToken, user)) {
		        var accessToken = service.generateToken(user);
		        revokeAllUserTokens(user);
		        saveUserToken(user, accessToken);
		        var authResponse = AuthenticationResponse.builder()
		                .accessToken(accessToken)
		                .refreshToken(refreshToken)
		                .build();
		        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
		      }
		    }
		  }
		  
		  // ?
		  @Transactional
		    public String confirmToken(String token) {
		        ConfirmationToken confirmationToken = confirmationTokenService
		                .getToken(token)
		                .orElseThrow(() ->
		                        new IllegalStateException("token not found"));

		        if (confirmationToken.getConfirmedAt() != null) {
		            throw new IllegalStateException("email already confirmed");
		        }

		        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

		        if (expiredAt.isBefore(LocalDateTime.now())) {
		            throw new IllegalStateException("token expired");
		        }

		        confirmationTokenService.setConfirmedAt(token);
		        //appUserService.enableAppUser(
		                //confirmationToken.getAppUser().getEmail());
		        return "confirmed";
		    }
		
		  private String buildEmail(String name, String link) {
		        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
		                "\n" +
		                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
		                "\n" +
		                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
		                "    <tbody><tr>\n" +
		                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
		                "        \n" +
		                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
		                "          <tbody><tr>\n" +
		                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
		                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
		                "                  <tbody><tr>\n" +
		                "                    <td style=\"padding-left:10px\">\n" +
		                "                  \n" +
		                "                    </td>\n" +
		                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
		                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
		                "                    </td>\n" +
		                "                  </tr>\n" +
		                "                </tbody></table>\n" +
		                "              </a>\n" +
		                "            </td>\n" +
		                "          </tr>\n" +
		                "        </tbody></table>\n" +
		                "        \n" +
		                "      </td>\n" +
		                "    </tr>\n" +
		                "  </tbody></table>\n" +
		                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
		                "    <tbody><tr>\n" +
		                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
		                "      <td>\n" +
		                "        \n" +
		                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
		                "                  <tbody><tr>\n" +
		                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
		                "                  </tr>\n" +
		                "                </tbody></table>\n" +
		                "        \n" +
		                "      </td>\n" +
		                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
		                "    </tr>\n" +
		                "  </tbody></table>\n" +
		                "\n" +
		                "\n" +
		                "\n" +
		                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
		                "    <tbody><tr>\n" +
		                "      <td height=\"30\"><br></td>\n" +
		                "    </tr>\n" +
		                "    <tr>\n" +
		                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
		                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
		                "        \n" +
		                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
		                "        \n" +
		                "      </td>\n" +
		                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
		                "    </tr>\n" +
		                "    <tr>\n" +
		                "      <td height=\"30\"><br></td>\n" +
		                "    </tr>\n" +
		                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
		                "\n" +
		                "</div></div>";
		    }
}
