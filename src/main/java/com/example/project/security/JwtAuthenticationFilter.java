package com.example.project.security;

import com.example.project.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/user/login")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

//            if (jwtTokenProvider.validateToken(token)) {
//                String email = jwtTokenProvider.extractEmail(token);
//
//                var user = userRepository.findByEmail(email).orElse(null);
//
//                if (user != null && Boolean.TRUE.equals(user.getIsActive())) {
//
//                    var auth = new UsernamePasswordAuthenticationToken(
//                            user,
//                            null,
//                            List.of(
//                                    new SimpleGrantedAuthority(
//                                            "ROLE_" + user.getRole().name()
//                                    )
//                            )
//                    );
//
//                    SecurityContextHolder.getContext()
//                            .setAuthentication(auth);
//                }
//            }
//        }
            if (jwtTokenProvider.validateToken(token)) {

                String email = jwtTokenProvider.extractEmail(token);

                CustomUserDetails userDetails =
                        (CustomUserDetails) userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }


        filterChain.doFilter(request, response);
    }
}
