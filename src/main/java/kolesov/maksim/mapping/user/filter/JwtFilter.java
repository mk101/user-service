package kolesov.maksim.mapping.user.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kolesov.maksim.mapping.user.model.UserEntity;
import kolesov.maksim.mapping.user.service.JwtService;
import kolesov.maksim.mapping.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            return null;
        }

        String token = header.split(" ")[1].trim();
        UUID id;
        try {
            id = UUID.fromString(jwtService.getSub(token));
        } catch (NullPointerException npe) {
            log.error("Failed to load sub from token");
            return null;
        }

        try {
            if (!jwtService.isAccess(token)) {
                return null;
            }
        } catch (Exception e) {
            log.error("Failed to load type from token");
            return null;
        }

        Optional<UserEntity> user = userService.findById(id);
        return user.orElse(null);

    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return "";
    }

}
