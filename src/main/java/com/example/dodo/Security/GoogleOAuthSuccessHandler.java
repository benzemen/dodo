
package com.example.dodo.Security;

import com.example.dodo.Entity.User;
import com.example.dodo.Repositories.UserRepository;
import com.example.dodo.Services.EmailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class GoogleOAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public GoogleOAuthSuccessHandler(
            UserRepository userRepository,
            JwtUtil jwtUtil,
            EmailService emailService) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        Optional<User> existingUser = userRepository.findByuseremail(email);

        User user;
        if (existingUser.isEmpty()) {
            user = new User();
            user.setUsername(name);
            user.setUseremail(email);
            user.setPassword("GOOGLE_AUTH"); // dummy password
            user.setProvider("GOOGLE");

            userRepository.save(user);

            // send email only on first registration
            emailService.sendEmail(email, name);
        } else {
            user = existingUser.get();
        }

        String token = jwtUtil.generateToken(user.getUseremail());

        response.sendRedirect(
                "http://localhost:3000/oauth-success?token=" + token
        );
    }
}
